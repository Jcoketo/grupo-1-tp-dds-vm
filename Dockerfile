FROM amazoncorretto:17-alpine-jdk
MAINTAINER Giansantucho
COPY /out/artifacts/tp_dds_g1_2024_jar/tp_dds_g1_2024.jar Giansantucho_app.jar
ENTRYPOINT ["java","-jar","/Giansantucho_app.jar"]