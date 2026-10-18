# =============================================================================
# Campus Placement & Internship Portal — Dockerfile
# Optimised for Railway deployment (also works on Render, Fly.io, any VPS).
#
# Build:
#   docker build -t campus-portal .
#
# Run locally:
#   docker run -p 8080:8080 \
#     -e DB_URL="jdbc:mysql://HOST:PORT/defaultdb?sslMode=REQUIRED&serverTimezone=UTC&connectTimeout=15000&socketTimeout=60000" \
#     -e DB_USERNAME="avnadmin" \
#     -e DB_PASSWORD="your_password" \
#     campus-portal
# =============================================================================

# ─────────────────────────────────────────────────────────────────────────────
# STAGE 1: Build — compile sources → WAR
# Uses the full Maven + JDK 11 image; discarded after build (keeps final image small).
# ─────────────────────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-11 AS build

WORKDIR /app

# Cache dependency downloads separately from source changes.
# Changing only source files will NOT re-download dependencies.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build the WAR
COPY src ./src
RUN mvn clean package -DskipTests -B

# ─────────────────────────────────────────────────────────────────────────────
# STAGE 2: Runtime — Tomcat 9 + JDK 11 (slim)
# Tomcat 9 is modern (Servlet 4.0), stable, and fully compatible with the app.
# ─────────────────────────────────────────────────────────────────────────────
FROM tomcat:9.0-jdk11-temurin

# Remove Tomcat's default sample webapps to keep the image clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy WAR as ROOT so the app is served at / instead of /campus-placement-portal.
# This is the standard convention for hosted deployments.
COPY --from=build /app/target/campus-placement-portal-1.0-SNAPSHOT.war \
     /usr/local/tomcat/webapps/ROOT.war

# Copy compiled classes + libs for the DB migration runner (run before Tomcat starts)
COPY --from=build /app/target/campus-placement-portal-1.0-SNAPSHOT/WEB-INF/classes \
     /migration/classes
COPY --from=build /app/target/campus-placement-portal-1.0-SNAPSHOT/WEB-INF/lib \
     /migration/lib

# ── Startup script ─────────────────────────────────────────────────────────
# Responsibilities:
#   1. Validate required DB environment variables (fail fast with a clear message)
#   2. Handle the PORT variable that Railway (and other platforms) inject
#   3. Run DB migration (idempotent — creates missing tables only)
#   4. Start Tomcat
RUN printf '#!/bin/sh\n\
set -e\n\
\n\
# ── 1. Validate required environment variables ─────────────────────────────\n\
missing=""\n\
for var in DB_URL DB_USERNAME DB_PASSWORD; do\n\
  eval val=\\$$var\n\
  if [ -z "$val" ]; then\n\
    missing="$missing $var"\n\
  fi\n\
done\n\
\n\
if [ -n "$missing" ]; then\n\
  echo ""\n\
  echo "================================================================"\n\
  echo "  CONFIGURATION ERROR: missing required environment variables:"\n\
  echo " $missing"\n\
  echo ""\n\
  echo "  Set them in your Railway / hosting platform environment"\n\
  echo "  settings, or with -e VAR=value in docker run."\n\
  echo "================================================================"\n\
  echo ""\n\
  exit 1\n\
fi\n\
\n\
# ── 2. Handle dynamic PORT (Railway injects $PORT; default to 8080) ────────\n\
PORT="${PORT:-8080}"\n\
echo "=== [start.sh] Will listen on port $PORT ==="\n\
\n\
# Patch Tomcat server.xml connector port at runtime\n\
sed -i "s/port=\\"8080\\"/port=\\"$PORT\\"/g" /usr/local/tomcat/conf/server.xml\n\
\n\
# ── 3. Run DB migration ────────────────────────────────────────────────────\n\
echo "=== [start.sh] Running DB migration ==="\n\
java -cp "/migration/classes:/migration/lib/*" com.placement.util.DbMigration \\\n\
  && echo "=== [start.sh] Migration OK ===" \\\n\
  || echo "=== [start.sh] Migration warning (table may already exist) ==="\n\
\n\
# ── 4. Start Tomcat ────────────────────────────────────────────────────────\n\
echo "=== [start.sh] Starting Tomcat on port $PORT ==="\n\
exec catalina.sh run\n' > /usr/local/bin/start.sh && chmod +x /usr/local/bin/start.sh

# Declare env var names — values are NEVER baked into the image.
# Supply them at runtime via docker run -e or your hosting platform.
ENV DB_URL="" \
    DB_USERNAME="" \
    DB_PASSWORD="" \
    PORT="8080"

# Railway health checks hit / — the ROOT.war will respond.
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:${PORT}/ || exit 1

EXPOSE 8080

CMD ["/usr/local/bin/start.sh"]
