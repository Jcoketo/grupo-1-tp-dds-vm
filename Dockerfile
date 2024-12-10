FROM maven:3.8.6-openjdk-18 AS build
COPY . .
RUN mvn clean package assembly:single -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build target/tp_dds_g1_2024-1.0-SNAPSHOT-jar-with-dependencies.jar main-project.jar
EXPOSE 8090
CMD ["java","-classpath","main-project.jar","Application"]