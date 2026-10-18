FROM eclipse-temurin:25.0.4.1_1-jdk-noble AS build
WORKDIR /workspace
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw -B dependency:go-offline
COPY src src
RUN ./mvnw -B -o package -DskipTests
RUN java -Djarmode=tools -jar target/*.jar extract --layers --destination extracted
FROM eclipse-temurin:25.0.4.1_1-jre-noble
WORKDIR /app
COPY --from=build /workspace/extracted/dependencies/ ./
COPY --from=build /workspace/extracted/spring-boot-loader/ ./
COPY --from=build /workspace/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/extracted/application/ ./
ENV SERVER_ADDRESS=0.0.0.0 SERVER_PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shop-0.0.1-SNAPSHOT.jar"]
