#!/bin/bash

cd ./backend || exit

# Parar o Spring Boot
echo "Parando Spring Boot..."
pkill -f 'mvn spring-boot:run' # Para todos os processos do Spring Boot

# Parar o Docker
echo "Parando Docker..."
docker-compose stop