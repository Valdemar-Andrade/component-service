# Component Service

Part of a distributed **Industry 4.0 Industrial Pipeline Simulator**, built with an event-driven microservices architecture.

---

## 🧠 System Overview

This project simulates a real industrial production pipeline, where independent services collaborate to produce goods.

Pipeline Flow:

Raw Material → Processing → Component Production → Product Assembly

Each step operates as an isolated microservice, communicating via Kafka events.

---

## 🎯 Function of this Service

The **Component Service** is responsible for producing industrial parts from processed materials.

It acts as the third stage of the pipeline, responsible for transforming usable materials into components ready for assembly.

Examples:

- Steel → Pistons, Crankshaft, Chassis
- Glass → Screen
- Rubber → Tires

---

## ⚙️ Responsibilities

- Consume processed material events from Kafka
- Validate the availability of necessary resources
- Apply compatibility rules (BOM - Bill of Materials)
- Produce industrial components
- Persist produced components
- Publish events for assembly service

---

## 🔄 Position in the Pipeline

[Raw Material Service] → [Processing Service] → [Component Service] → [Assembly Service]

---

## 📡 Event-Driven Communication

### Events Consumed

- `MATERIAL_PROCESSED`

### Events Produced

- `COMPONENT_CREATED`

---

## 📦 Event Structure

### Entry Event
```
{
"eventId": "uuid",

"eventType": "MATERIAL_PROCESSED",

"timestamp": 1710000000,

"sourceService": "processing-service",

"targetService": "component-service",

"payload": {

"name": "Steel",

"quantity": 8

}
}
```

### Exit Event

```
{
"eventId": "uuid",

"eventType": "COMPONENT_CREATED",

"timestamp": 1710000000,

"sourceService": "component-service",

"targetService": "assembly-service",

"payload": {

"name": "Engine",

"quantity": 1,

"components": [
{ "name": "Piston", "quantity": 4 },
{ "name": "Crankshaft", "quantity": 1 },
{ "name": "Engine Block", "quantity": 1 }

]

}
}
```

---

## 🧩 BOM (Bill of Materials) Validation

This service implements mandatory component structure validation.

Example: Engine

### Engine
```
├── Pistons (4x) → Steel
├── Crankshaft (1x) → Steel
├── Block (1x) → Aluminum
├── Cylinder Head (1x) → Aluminum
```

Rules applied:

- No component is produced without sufficient materials
- Dependencies must be met before production
- Compatibility between components must be validated
- Each component maintains a reference to the producer

---

## ⏱️ Production Pipeline (Latency Simulation)

Component production follows stages with defined durations.

Example: Engine Production

```
[
{ "name": "PARTS_PREPARATION", "durationMs": 6000 },
{ "name": "ASSEMBLY", "durationMs": 10000 },
{ "name": "QUALITY_CONTROL", "durationMs": 4000 }

]
```

This simulates real-time industrial production.

---

**Note:** In normal operation, production is automatically triggered by Kafka events.

---

## 🔄 Internal Flow

Receive Kafka event (MATERIAL_PROCESSED)
Validate material availability
Validate BOM rules
Execute production pipeline (with delay)
Create component
Persist in the database Publish COMPONENT_CREATED event

---

## 🗄️ Data Ownership

This service follows the principles of microservices architecture:

## Own database
- No direct access to data from other services
- Communication strictly via Kafka events

---

## 🧱 Technologies

- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker

---

## Running the Service

- docker-compose up --build

---

## 🧠 Key Concepts Demonstrated

- Bill of Materials Validation (BOM)

- Dependency management in distributed systems
- Event-driven production
- Simulation of industrial pipelines with latency
- Consistency and decoupling between services

---

## Other Services:

- [raw-material-service](https://github.com/Valdemar-Andrade/raw-material-service.git)
- [processing-service](https://github.com/Valdemar-Andrade/processing-service.git)

---

## 👤 Developer

- GitHub: [@Valdemar-Andrade]
- LinkedIn: [Valdemar Andrade](https://www.linkedin.com/in/valdemar-andrade-8b0b45189)
