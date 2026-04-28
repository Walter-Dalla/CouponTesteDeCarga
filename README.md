# Coupon API

REST API para gerenciamento de cupons de desconto, desenvolvida para analise de codigos e testes de carga.

## Stack

- **Java 25** + **Spring Boot 4.0.6**
- **Spring Data JPA** + **H2** (in-memory)
- **Springdoc OpenAPI 3** (Swagger UI)
- **Lombok**
- **Docker** + **Cloudflare Tunnel**

---

## Demonstração ao vivo

A aplicação está publicada em servidor local com DNS reverso via **Cloudflared**:

- **Swagger UI:** https://coupon.walterdalla.uk/swagger-ui/index.html

O servidor local expõe a porta `5071` mapeada para o container (`8080`). O Cloudflare Tunnel cria um túnel seguro HTTPS sem necessidade de IP público ou abertura de portas no roteador.

---

## Endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/coupon` | Cria novo cupom |
| `GET` | `/coupon/{id}` | Busca cupom por UUID |
| `DELETE` | `/coupon/{id}` | Soft delete do cupom |

### POST /coupon

```json
{
  "code": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Cupom de 10% de desconto",
  "discountValue": 10.5,
  "expirationDate": "2027-01-01T00:00:00Z",
  "published": true
}
```

### GET /coupon/{id}

Retorna o cupom com os campos calculados `redeemed` e `published` derivados do `status`.

### DELETE /coupon/{id}

Soft delete — marca `deleted = true` e status `DELETED`. Cupom some de todas as queries via `@SQLRestriction("deleted = false")`. Retorna `400` se cupom já foi deletado.

---

## Decisões técnicas

### Status unificado vs campos independentes

O desafio técnico não especificava se `published` e `redeemed` seriam campos independentes do status. Foi adotado um modelo unificado com `CouponStatus { ACTIVE, INACTIVE, DELETED }`:

- `published` → derivado: `status == ACTIVE`
- `redeemed` → derivado: `status == INACTIVE || deleted`

Campos independentes (`deleted`, `status`, `redeemed`, `published`) podem gerar dessincronização no banco de dados. Além disso, expor ao usuário se um cupom "já existiu e foi deletado" é uma falha de segurança — o comportamento correto é tratar cupom deletado como inexistente.

### Soft delete com @SQLRestriction

`@SQLRestriction("deleted = false")` no Hibernate aplica o filtro em **todas** as queries automaticamente, sem necessidade de sobrescrever métodos do repositório. Após delete, `GET /coupon/{id}` retorna `404` naturalmente.

### @PathVariable vs @ModelAttribute

O controller preferencialmente usaria `@ModelAttribute` nos endpoints `GET` e `DELETE` para receber os parâmetros como objeto (facilitando validação e extensão sem alterar a assinatura do método). Porém, o Swagger não interpreta `@ModelAttribute` em requests `GET`/`DELETE` como path variable — gera documentação incorreta. Por isso foi adotado `@PathVariable UUID code` diretamente.

---

## Rodar localmente

### Pré-requisitos

- Docker instalado

### Usando compose.yaml (build local)

```bash
docker compose up --build
```

A aplicação sobe na porta `8080`:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console

> **H2 Console:** JDBC URL `jdbc:h2:mem:coupondb`, usuário `sa`, senha em branco.

---

## Deploy em servidor

### Usando docker-compose.prod.yml (pull do Docker Hub)

```bash
docker compose -f docker-compose.prod.yml up -d
```

Baixa a imagem `walterdallatorre/coupon-load-test:latest` do Docker Hub e sobe na porta `5071`.

O arquivo `docker-compose.prod.yml` conecta o container à rede `cloudflared_network`, que é gerenciada pelo Cloudflare Tunnel (`cloudflared`). O túnel roteia o domínio `coupon.walterdalla.uk` para o container sem exposição direta de portas ao público.

### Publicar nova versão

```bash
docker build -t walterdallatorre/coupon-load-test:latest .
docker push walterdallatorre/coupon-load-test:latest
```

---

## Teste de Carga com Locust

O arquivo `locustfile.py` simula tráfego realista nos três endpoints da API.

### Distribuição de tarefas

| Task | Peso | Endpoint |
|------|------|----------|
| GET existente | 5 | `GET /coupon/{code}` |
| POST criar | 3 | `POST /coupon` |
| DELETE | 1 | `DELETE /coupon/{code}` |
| GET inexistente | 1 | `GET /coupon/{uuid-aleatorio}` (estresa path 404) |

### Pré-requisitos

```bash
pip install locust
```

### Rodar com UI (recomendado)

```bash
cd coupon
locust --host=http://localhost:8080
```

Acesse `http://localhost:8089`, configure número de usuários e taxa de spawn, e inicie o teste.

Para rodar contra produção:

```bash
locust --host=https://coupon.walterdalla.uk
```

### Rodar headless (CI / automação)

```bash
locust --host=http://localhost:8080 \
       --users=100 --spawn-rate=10 \
       --run-time=2m --headless \
       --csv=results/load
```

Gera arquivos `results/load_stats.csv`, `results/load_failures.csv` e `results/load_stats_history.csv` com os resultados.
