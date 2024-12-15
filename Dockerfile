FROM maven:3.8.6-openjdk-18 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tp_dds_g1_2024-1.0-SNAPSHOT.jar tp_dds_g1_2024.jar
COPY --from=build /app/src/main/resources /app/static
EXPOSE 8090
CMD ["java", "-jar", "tp_dds_g1_2024.jar"]