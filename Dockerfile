# ==========================================
# STAGE 1: Build the Java WAR file using Maven
# ==========================================
FROM maven:3.9-eclipse-temurin-11 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Run the WAR file on Tomcat 9
# ==========================================
FROM tomcat:9.0-jdk11

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/campus-placement-portal-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat's default port so Render knows where to route traffic
EXPOSE 8080

CMD ["catalina.sh", "run"]
