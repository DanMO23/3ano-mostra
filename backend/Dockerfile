# Usar a imagem oficial do MySQL
FROM mysql:5.7

# Copiar o script de inicialização para o diretório correto
COPY init.sql /docker-entrypoint-initdb.d/

# Definir variáveis de ambiente para o MySQL
ENV MYSQL_ROOT_PASSWORD=projeto#Integrador123
ENV MYSQL_DATABASE=pi_reinados_web
