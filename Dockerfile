FROM openjdk:15
ARG JAR_FILE
ADD /target/${JAR_FILE} /rodent.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=oic", "/rodent.jar"]
