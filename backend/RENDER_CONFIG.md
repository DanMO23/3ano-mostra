# ⚙️ Configuração Rápida - Render

## 🐳 Runtime
**Docker** (não Java)

## 📁 Configuração do Serviço
- **Root Directory**: `backend`
- **Dockerfile Path**: (deixe em branco, detecta automaticamente)

## 🔐 Variáveis de Ambiente Obrigatórias

```bash
PORT=10000
DB_HOST=<do-render-mysql>
DB_PORT=3306
DB_NAME=pi_reinados_web
DB_USER=<do-render-mysql>
DB_PASSWORD=<do-render-mysql>
DB_USE_SSL=true
```

## ✅ Variáveis Opcionais

```bash
LOG_LEVEL=INFO
SQL_LOG_LEVEL=WARN
SHOW_SQL=false
```

## 🏥 Health Check
Path: `/actuator/health`

## 📝 Checklist
- [ ] Runtime: **Docker** (não Java!)
- [ ] Root Directory: `backend`
- [ ] Todas as 7 variáveis configuradas
- [ ] `DB_USE_SSL=true` ⚠️ OBRIGATÓRIO
- [ ] Health Check configurado

Pronto para deploy! 🚀
