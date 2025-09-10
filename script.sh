#!/bin/bash

# Mudar para o diretório do backend
cd ./backend || exit

# Executar o docker-compose
echo "Iniciando Docker..."
docker-compose up -d

# Aguardar um pouco para garantir que os serviços estejam prontos
sleep 10

# Rodar o Spring Boot em segundo plano e redirecionar logs
echo "Iniciando Spring Boot..."
mvn spring-boot:run > spring.log 2>&1 &

# Mudar para a pasta do React
cd ../
cd ./ReinadosDaWeb/core/bin || exit

# Iniciar o aplicativo React
echo "Iniciando React..."
npm start