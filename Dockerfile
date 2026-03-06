# -------- BUILD STAGE --------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests


# -------- RUNTIME STAGE --------
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]