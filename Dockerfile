FROM gradle:8.7 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test
FROM tomcat:10-jdk21
COPY --from=build /app/build/libs/skycast-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/skycast.war
EXPOSE 8080