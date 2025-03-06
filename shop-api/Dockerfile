FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
ARG CONTAINER_PORT
COPY pom.xml /app
RUN mvn dependency:resolve
COPY .  /app
RUN mvn clean
RUN mvn package -DskipTests -X

FROM  eclipse-temurin:21-jdk-jammy AS runner
COPY --from=build /app/target/*.jar app.jar
EXPOSE ${CONTAINER_PORT}
CMD ["java","-jar","app.jar"]