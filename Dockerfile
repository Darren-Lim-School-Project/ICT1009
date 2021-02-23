FROM openjdk:15
ARG JAR_FILE
ADD /target/${JAR_FILE} /rodent.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone='Asia/Singapore'", "-Dspring.profiles.active=oic", "/rodent.jar"]
