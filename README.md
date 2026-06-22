# 🛒 iPurchases

Sistema distribuído baseado em microsserviços para gerenciamento de compras, faturamento e logística, utilizando comunicação síncrona via REST e comunicação assíncrona orientada a eventos com Apache Kafka.

## 📖 Visão Geral

O iPurchases foi desenvolvido para demonstrar conceitos modernos de arquitetura de software utilizando:

* Microsserviços
* Apache Kafka
* Spring Boot
* PostgreSQL
* MinIO
* Docker
* Arquitetura Orientada a Eventos (EDA)

A solução simula o fluxo completo de uma compra, desde o cadastro de clientes e produtos até a geração da nota fiscal e envio do pedido.

---

# 🏗 Arquitetura

## Microsserviços

### Customer Service

Responsável pelo gerenciamento dos clientes.

Principais funcionalidades:

* Cadastro de clientes
* Consulta de clientes
* Atualização de clientes
* Exclusão de clientes

Banco de dados:

```text
PostgreSQL
```

---

### Product Service

Responsável pelo gerenciamento dos produtos disponíveis para venda.

Principais funcionalidades:

* Cadastro de produtos
* Consulta de produtos
* Atualização de estoque
* Exclusão de produtos

Banco de dados:

```text
PostgreSQL
```

---

### Order Service

Responsável pela criação e gerenciamento dos pedidos.

Principais funcionalidades:

* Criação de pedidos
* Consulta de pedidos
* Processamento de pagamento
* Atualização de status

Banco de dados:

```text
PostgreSQL
```

---

### Invoicing Service

Responsável pela emissão e armazenamento das notas fiscais.

Principais funcionalidades:

* Geração de nota fiscal
* Armazenamento de documentos
* Publicação de eventos de faturamento

Banco de dados:

```text
PostgreSQL
```

Armazenamento:

```text
MinIO
```

---

### Logistics Service

Responsável pelo rastreamento e envio dos pedidos.

Principais funcionalidades:

* Cadastro de envio
* Rastreamento
* Atualização logística

Banco de dados:

```text
PostgreSQL
```

---

# 🔄 Fluxo de Negócio

## 1. Criação do Pedido

O usuário realiza uma compra através dos canais WEB ou Mobile.

O Order Service recebe a solicitação e cria o pedido.

```text
Usuário → Order Service
```

---

## 2. Processamento do Pagamento

Após a criação do pedido, o Order Service envia os dados da transação para o sistema bancário responsável pelo pagamento.

```text
Order Service → Banco
```

---

## 3. Recebimento do Status do Pagamento

O banco retorna o resultado da transação.

Possíveis status:

* Aprovado
* Recusado
* Em análise

```text
Banco → Order Service
```

---

## 4. Publicação do Evento de Pedido Pago

Quando o pagamento é aprovado, o Order Service publica um evento no Kafka.

Tópico:

```text
pedidos-pagos
```

Evento:

```json
{
  "orderId": 1,
  "customerId": 10,
  "total": 250.00,
  "status": "PAID"
}
```

---

## 5. Geração da Nota Fiscal

O Invoicing Service consome o evento de pedido pago.

Após processar o evento:

* Gera a nota fiscal
* Salva o documento no MinIO
* Persiste os dados no banco

```text
Kafka → Invoicing Service
```

---

## 6. Publicação do Evento de Faturamento

Após gerar a nota fiscal, o Invoicing Service publica um novo evento.

Tópico:

```text
pedidos-faturados
```

Evento:

```json
{
  "orderId": 1,
  "invoiceNumber": "NF-0001"
}
```

---

## 7. Processamento Logístico

O Logistics Service consome o evento de faturamento.

Após receber o evento:

* Cria o rastreamento
* Define o status inicial da entrega
* Registra o envio

```text
Kafka → Logistics Service
```

---

## 8. Publicação do Evento de Envio

Quando o envio é criado, o Logistics Service publica um novo evento.

Tópico:

```text
pedidos-enviados
```

Evento:

```json
{
  "orderId": 1,
  "trackingCode": "BR123456789"
}
```

Esse evento poderá ser consumido por futuras aplicações de notificação, monitoramento ou analytics.

---

# 📨 Comunicação Assíncrona

## Apache Kafka

### Tópicos

| Tópico            | Produtor          | Consumidor        |
| ----------------- | ----------------- | ----------------- |
| pedidos-pagos     | Order Service     | Invoicing Service |
| pedidos-faturados | Invoicing Service | Logistics Service |
| pedidos-enviados  | Logistics Service | Outros serviços   |

---

# 🗄 Persistência

Cada microsserviço possui seu próprio banco de dados.

```text
Customer Service  → PostgreSQL
Product Service   → PostgreSQL
Order Service     → PostgreSQL
Invoicing Service → PostgreSQL
Logistics Service → PostgreSQL
```

Essa abordagem garante:

* Baixo acoplamento
* Independência dos serviços
* Escalabilidade
* Autonomia de domínio

---

# 📁 Armazenamento de Arquivos

O sistema utiliza MinIO para armazenamento das notas fiscais.

Funcionalidades:

* Upload de PDFs
* Download de documentos
* Compatibilidade com Amazon S3

---

# 🚀 Tecnologias Utilizadas

## Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Validation
* OpenFeign

## Mensageria

* Apache Kafka
* Kafka UI

## Banco de Dados

* PostgreSQL

## Armazenamento

* MinIO

## Testes

* JUnit 5
* Mockito

## DevOps

* Docker
* Docker Compose

---

# 🎯 Objetivos do Projeto

Este projeto foi desenvolvido para aprofundar conhecimentos em:

* Microsserviços
* Arquitetura Orientada a Eventos
* Apache Kafka
* Comunicação Assíncrona
* Persistência Distribuída
* Spring Boot
* Docker
* Integração entre Serviços
* Boas Práticas de Arquitetura

---

# 🔮 Melhorias Futuras

* API Gateway
* Service Discovery (Eureka)
* Resilience4j
* Observabilidade com Prometheus e Grafana
* Distributed Tracing
* Kubernetes
* CI/CD com GitHub Actions
* Autenticação JWT centralizada
