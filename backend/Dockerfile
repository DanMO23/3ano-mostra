# ---- Estágio 1: Build ----
# Usamos uma imagem oficial do Maven com Java 17 para compilar nosso projeto
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de dependências primeiro para aproveitar o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar
RUN mvn package -DskipTests


# ---- Estágio 2: Run ----
# Usamos uma imagem Java 17 mais leve, apenas com o necessário para rodar
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o .jar que foi gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que o Spring Boot usa por padrão
EXPOSE 8080

# Comando para iniciar a aplicação quando o container rodar
ENTRYPOINT ["java", "-jar", "app.jar"]