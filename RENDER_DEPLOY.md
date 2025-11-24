# Deploy no Render - Backend Spring Boot com Docker

## Configuração do Banco de Dados MySQL no Render

### 1. Criar o serviço MySQL
1. No Render Dashboard, clique em **New +** → **MySQL**
2. Configure:
   - **Name**: `pi-reinados-mysql` (ou nome de sua escolha)
   - **Database**: `pi_reinados_web`
   - **User**: `root` (ou crie um usuário específico)
   - **Region**: Escolha a mesma região do backend
3. Anote as credenciais geradas (Internal Database URL)

### 2. Criar o serviço Web (Backend Spring Boot)

1. No Render Dashboard, clique em **New +** → **Web Service**
2. Conecte seu repositório GitHub
3. Configure:
   - **Name**: `pi-reinados-backend`
   - **Region**: Mesma do banco de dados
   - **Branch**: `main`
   - **Root Directory**: `backend`
   - **Runtime**: `Docker`
   - **Dockerfile Path**: `Dockerfile` (deixe em branco, o Render detectará automaticamente)
   
   **IMPORTANTE**: O Render usará o Dockerfile para build e deploy automaticamente.

### 3. Configurar Variáveis de Ambiente

No painel do Web Service, vá em **Environment** e adicione as seguintes variáveis:

```bash
# Porta (obrigatório para Render)
PORT=10000

# Configurações do Banco de Dados (use os valores do seu MySQL no Render)
DB_HOST=<seu-mysql-host>.oregon-postgres.render.com
DB_PORT=3306
DB_NAME=pi_reinados_web
DB_USER=root
DB_PASSWORD=<sua-senha-mysql>
DB_USE_SSL=true

# Logging (opcional - reduz logs em produção)
LOG_LEVEL=INFO
SQL_LOG_LEVEL=WARN
SHOW_SQL=false
```

### 4. Formato da Connection String

Se você tiver o **Internal Database URL** do Render (formato: `mysql://user:password@host:port/database`), extraia:

**Exemplo:**
```
mysql://root:abc123def456@dpg-xyz123.oregon-postgres.render.com/pi_reinados_web
```

Extraia e configure:
- `DB_HOST`: `dpg-xyz123.oregon-postgres.render.com`
- `DB_PORT`: `3306`
- `DB_USER`: `root`
- `DB_PASSWORD`: `abc123def456`
- `DB_NAME`: `pi_reinados_web`
- `DB_USE_SSL`: `true` ⚠️ **OBRIGATÓRIO no Render**

### 5. Health Check (Recomendado)

Configure o Health Check no Render:
1. Vá em **Settings** do seu Web Service
2. **Health Check Path**: `/actuator/health`
3. Salve

Para que funcione, adicione ao `pom.xml` (já deve estar incluído):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 6. Troubleshooting

#### Erro: "Unable to open JDBC Connection"

**Soluções:**
1. ✅ Verifique se `DB_USE_SSL=true` está configurado
2. ✅ Confirme que todas as variáveis de ambiente estão corretas
3. ✅ Verifique se o banco MySQL está ativo no Render
4. ✅ Teste a conexão manualmente:
   ```bash
   mysql -h <DB_HOST> -P 3306 -u <DB_USER> -p<DB_PASSWORD> <DB_NAME> --ssl-mode=REQUIRED
   ```

#### Erro: "Port already in use" ou "Cannot bind to port"

O Render injeta automaticamente a variável `PORT`. Certifique-se de:
- ✅ Variável `PORT=10000` configurada no Render
- ✅ O Dockerfile usa `${PORT}` corretamente (já configurado)

#### Build falha no Render

1. Verifique os logs de build
2. Confirme que o `Dockerfile` está na raiz de `backend/`
3. Teste o build localmente:
   ```bash
   cd backend
   docker build -t test-backend .
   docker run -p 8080:8080 test-backend
   ```

#### Container inicia mas a aplicação não responde

1. Verifique os logs do container no Render
2. Confirme que a porta está correta (`PORT=10000`)
3. Teste localmente com variáveis de ambiente:
   ```bash
   docker run -p 8080:8080 \
     -e DB_HOST=localhost \
     -e DB_PORT=3307 \
     -e DB_NAME=pi_reinados_web \
     -e DB_USER=root \
     -e DB_PASSWORD=projeto#Integrador123 \
     -e DB_USE_SSL=false \
     -e PORT=8080 \
     test-backend
   ```

### 7. Deploy e Monitoramento

**Deploy Automático:**
- O Render detecta automaticamente mudanças no branch `main` e faz deploy

**Deploy Manual:**
1. Vá no Dashboard do seu Web Service
2. Clique em **Manual Deploy** → **Deploy latest commit**

**Ver Logs em Tempo Real:**
- No Dashboard: **Logs** tab
- Ou via CLI: `render logs --tail`

**Monitoramento:**
- Métricas disponíveis no Dashboard
- Configure alertas em Settings

### 8. Otimizações de Performance

#### Ajustar memória do container
Adicione ao `application.properties`:
```properties
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

#### Configurar connection pool
Já configurado no `application.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.connection-timeout=60000
```

### 9. Configuração de CORS (se necessário)

Se o frontend estiver em outro domínio, adicione variável:
```bash
CORS_ORIGINS=https://seu-frontend.render.com,http://localhost:3000
```

E configure no código (criar classe de configuração se necessário).

## Checklist Final para Render

- [ ] Banco MySQL criado no Render
- [ ] Web Service configurado com **Runtime: Docker**
- [ ] Root Directory: `backend`
- [ ] Dockerfile existe em `backend/Dockerfile`
- [ ] Variáveis de ambiente configuradas:
  - [ ] `PORT=10000`
  - [ ] `DB_HOST` (do MySQL do Render)
  - [ ] `DB_PORT=3306`
  - [ ] `DB_NAME=pi_reinados_web`
  - [ ] `DB_USER` (do MySQL do Render)
  - [ ] `DB_PASSWORD` (do MySQL do Render)
  - [ ] `DB_USE_SSL=true` ⚠️ **OBRIGATÓRIO**
- [ ] Health Check configurado: `/actuator/health`
- [ ] Deploy executado com sucesso
- [ ] Logs sem erros de conexão
- [ ] Aplicação respondendo nas rotas

## Comandos Úteis

**Testar build local:**
```bash
cd backend
docker build -t pi-backend .
```

**Rodar localmente com Docker:**
```bash
docker run -p 8080:8080 \
  -e PORT=8080 \
  -e DB_HOST=localhost \
  -e DB_PORT=3307 \
  -e DB_NAME=pi_reinados_web \
  -e DB_USER=root \
  -e DB_PASSWORD=projeto#Integrador123 \
  -e DB_USE_SSL=false \
  pi-backend
```

**Ver logs do container:**
```bash
docker logs <container-id>
```

## Recursos Adicionais

- [Render Docker Deployment](https://render.com/docs/deploy-an-image)
- [Render Environment Variables](https://render.com/docs/environment-variables)
- [Render MySQL Docs](https://render.com/docs/databases)
