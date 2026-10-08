# ==========================================
# STAGE 1: Build the Java WAR file using Maven
# ==========================================
FROM maven:3.9-eclipse-temurin-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the actual source code and build the WAR file
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Run the WAR file on Tomcat 9
# ==========================================
FROM tomcat:9.0-jdk11

# Clear default Tomcat webapps (ROOT, examples, docs, etc.)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file from the first stage.
# We rename it to "ROOT.war" so the application runs at the root domain (/)
# instead of (/campus-placement-portal).
COPY --from=build /app/target/campus-placement-portal-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Replace Tomcat's default port (8080) with Render's dynamic $PORT environment variable
# Render injects a PORT env var automatically. If not, default to 8080.
RUN sed -i 's/port="8080"/port="${PORT}"/g' /usr/local/tomcat/conf/server.xml

# Start Tomcat
CMD ["catalina.sh", "run"]
