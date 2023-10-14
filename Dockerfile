FROM eclipse-temurin:19-jdk-alpine
LABEL authors="vera3"
VOLUME /tmp
WORKDIR .
ARG JAR_FILE=target/RealtyAgency-0.0.1-SNAPSHOT.jar
COPY /target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
ENV DATABASE_URL jdbc:postgresql://dpg-ckhbgjmafg7c73956cs0-a.oregon-postgres.render.com:5432/realty_db_v7b3
ENV DATABASE_USERNAME realty_db_v7b3_user
ENV DATABASE_PASSWORD nPicQ7xgEPbZgFDH6UdAGLcJ3YbVha6C
EXPOSE 8081