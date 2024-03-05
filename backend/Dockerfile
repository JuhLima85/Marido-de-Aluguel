FROM openjdk:11-jdk-slim as builder

# Etapa 1: Copiar apenas os artefatos necessários
WORKDIR /app
COPY target/marido-de-aluguel.jar /app/app.jar

# Etapa 2: Construir a imagem final
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=builder /app/app.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
