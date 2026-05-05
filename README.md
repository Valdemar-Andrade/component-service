# Serviço de Componentes

Parte de um **Simulador de Pipeline Industrial da Indústria 4.0** distribuído, construído com arquitetura de micro-serviços orientada a eventos.

---

## 🧠 Visão Geral do Sistema

Este projeto simula um pipeline de produção industrial real, onde serviços independentes colaboram para produzir bens.

Fluxo do pipeline:

Matéria-prima → Processamento → Produção de Componentes → Montagem do Produto

Cada etapa opera como um microsserviço isolado, comunicando através de eventos Kafka.

---

## 🎯 Função deste Serviço

O **Serviço de Componentes** é responsável pela produção de peças industriais a partir de materiais processados.

Atua como a terceira etapa do pipeline, sendo responsável por transformar materiais utilizáveis em componentes prontos para montagem.

Exemplos:
- Aço → Pistões, Cambota, Chassis
- Vidro → Ecrã
- Borracha → Pneus

---

## ⚙️ Responsabilidades

- Consumir eventos de materiais processados do Kafka
- Validar a disponibilidade de recursos necessários
- Aplicar regras de compatibilidade (BOM - Bill of Materials)
- Produzir componentes industriais
- Persistir componentes produzidos
- Publicar eventos para o serviço de montagem

---

## 🔄 Posição no Pipeline

[ Serviço de Matéria-Prima ] → [ Serviço de Processamento ] → [ Serviço de Componentes ] → [ Serviço de Montagem ]

---

## 📡 Comunicação Orientada a Eventos

### Eventos Consumidos

- `MATERIAL_PROCESSED`

### Eventos Produzidos

- `COMPONENT_CREATED`

---

## 📦 Estrutura do Evento

### Evento de Entrada
```
{
"eventId": "uuid",

"eventType": "MATERIAL_PROCESSED",

"timestamp": 1710000000,

"sourceService": "processing-service",

"targetService": "component-service",

"payload": {

"name": "Steel",

"quantidade": 8

}
}
```

### Evento de Saída

```
{
"eventId": "uuid",

"eventType": "COMPONENT_CREATED",

"timestamp": 1710000000,

"sourceService": "component-service",

"targetService": "assembly-service",

"payload": {

"name": "Engine",

"quantidade": 1,

"components": [
{ "name": "Piston", "quantidade": 4 },
{ "name": "Crankshaft", "quantidade": 1 },
{ "name": "Engine Block", "quantidade": 1 }
]

}
}
```

---

## 🧩 Validação de BOM (Bill of Materials)

Este serviço implementa validação obrigatória de estrutura de componentes.

Exemplo: Motor


### Motor
```
├── Pistões (4x) → Aço
├── Cambota (1x) → Aço
├── Bloco (1x) → Alumínio
├── Cabeçote (1x) → Alumínio
```

Regras aplicadas:

- Nenhum componente é produzido sem materiais suficientes
- Dependências devem ser satisfeitas antes da produção
- Compatibilidade entre componentes deve ser validada
- Cada componente mantém referência ao produtor

---

## ⏱️ Pipeline de Produção (Simulação de Latência)

A produção de componentes segue etapas com duração definida.

Exemplo: Produção de Motor

```
[
{ "name": "PREPARAÇÃO_DE_PARTES", "durationMs": 6000 },
{ "name": "MONTAGEM", "durationMs": 10000 },
{ "name": "CONTROLO_DE_QUALIDADE", "durationMs": 4000 }
]
```

Isto simula o tempo real de produção industrial.

---

**Nota:** Em funcionamento normal, a produção é acionada automaticamente por eventos Kafka.

---

## 🔄 Fluxo Interno

Receber evento Kafka (MATERIAL_PROCESSED)  
Validar disponibilidade de materiais  
Validar regras de BOM  
Executar pipeline de produção (com atraso)  
Criar componente  
Persistir na base de dados  
Publicar evento COMPONENT_CREATED  

---

## 🗄️ Propriedade dos Dados

Este serviço segue os princípios da arquitetura de micro-serviços:

## Base de dados própria
- Sem acesso direto aos dados de outros serviços
- Comunicação estritamente via eventos Kafka

---

## 🧱 Tecnologias

- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker

---

## Executar o Serviço

- docker-compose up --build

---

## 🧠 Conceitos-chave Demonstrados

- Validação de Bill of Materials (BOM)
- Gestão de dependências em sistemas distribuídos
- Produção orientada a eventos
- Simulação de pipelines industriais com latência
- Consistência e desacoplamento entre serviços

---

## Outros Serviços:

- [raw-material-service](https://github.com/Valdemar-Andrade/raw-material-service.git)
- [processing-service](https://github.com/Valdemar-Andrade/processing-service.git)

---

## 👤 Desenvolvedor

- GitHub: [@Valdemar-Andrade]  
- LinkedIn: [Valdemar Andrade](https://www.linkedin.com/in/valdemar-andrade-8b0b45189)
