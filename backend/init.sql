-- Script de inicialização do banco de dados
-- Este script é executado automaticamente quando o container MySQL é criado pela primeira vez

-- Criar o database (já é criado pelo docker-compose, mas deixo aqui como referência)
-- CREATE DATABASE IF NOT EXISTS pi_reinados_web CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar o database
USE pi_reinados_web;

-- O Hibernate criará as tabelas automaticamente com ddl-auto=update
-- Se você precisar criar tabelas manualmente ou inserir dados iniciais, adicione aqui

-- Exemplo: Inserir dados iniciais (descomente se necessário)
-- INSERT INTO alguma_tabela (campo1, campo2) VALUES ('valor1', 'valor2');

-- Garantir que o usuário root tem permissões
-- GRANT ALL PRIVILEGES ON pi_reinados_web.* TO 'root'@'%';
-- FLUSH PRIVILEGES;
