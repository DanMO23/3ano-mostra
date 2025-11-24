# ---- Estágio 1: Build ----
# Usamos uma imagem oficial do Maven com Java 17 para compilar nosso projeto














.mvn/wrapper/maven-wrapper.jar*.iml.idea/.vscode/node_modules/.DS_Store.env*.md*.logRENDER_DEPLOY.mdREADME.md.gitignore.gitFROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o wrapper do Maven (se existir)
COPY mvnw .
COPY .mvn .mvn

# Copia o arquivo de dependências primeiro para aproveitar o cache do Docker
COPY pom.xml .

# Baixa as dependências
RUN mvn dependency:go-offline -B || true

# Copia o resto do código fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar
RUN mvn clean package -DskipTests -B


# ---- Estágio 2: Run ----
# Usamos uma imagem Java 17 mais leve, apenas com o necessário para rodar
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Instala curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copia apenas o .jar que foi gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta (Render usa a variável PORT)
EXPOSE ${PORT:-8080}

# Adiciona um usuário não-root para segurança
RUN useradd -m -u 1001 -s /bin/bash appuser && chown -R appuser:appuser /app
USER appuser

# Comando para iniciar a aplicação quando o container rodar
# Render injeta a variável PORT automaticamente
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -Xmx512m -Xms256m -jar app.jar"]