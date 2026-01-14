# 📈 FinStream: Real-Time Reactive Trading Engine

FinStream is a high-performance, non-blocking trading backend built to simulate the core architecture of modern fintech platforms like **Groww**. It leverages a fully reactive stack to handle live market data streaming, atomic trade execution, and real-time portfolio management.

---

## 🏗️ System Architecture

The application is built using a **Reactive Event-Driven Architecture (EDA)** to ensure sub-millisecond data delivery and high scalability.

[Image of a reactive event-driven architecture diagram showing Spring WebFlux, Redis Pub/Sub, and PostgreSQL]

### 🚀 Technical Stack
* **Backend:** Java 21, Spring Boot 3.4
* **Reactive Core:** Project Reactor (Spring WebFlux)
* **Message Broker:** Redis (Pub/Sub for live price broadcasting)
* **Database:** PostgreSQL with R2DBC (Reactive Relational Database Connectivity)
* **Resilience:** Resilience4j (Circuit Breaker & Retry patterns)
* **Infrastructure:** Docker & Docker Compose
* **Tools:** Lombok, SLF4J

---

## 🔥 Key Features

### 1. Real-Time Price Engine
A background **Price Simulator** that mimics market volatility using a "Random Walk" algorithm.
* Broadcasts updates every **1 second**.
* Uses **Redis Pub/Sub** to decouple the data producer from consumers.
* Delivers data to clients via **Server-Sent Events (SSE)** for zero-refresh updates.

### 2. Transactional Order Management
A robust engine for executing BUY/SELL orders.
* **Optimistic Locking:** Prevents race conditions (e.g., double-spending) using `@Version`.
* **Atomicity:** Ensures that balance deductions and holding updates happen in a single transaction or not at all.
* **Fault Tolerance:** Implements a **Circuit Breaker** to protect the system during high-load periods or database latency.

### 3. Live Portfolio & P&L
* **Weighted Average Price:** Automatically calculates the cost basis as users buy more of a stock.
* **Reactive Merging:** Combines persistent holding data from PostgreSQL with live price ticks from Redis using `Flux.zip`.

---

## 🛠️ Installation & Setup

### Prerequisites
* Docker & Docker Compose
* JDK 21
* Gradle

### Step 1: Clone and Start Infrastructure
```bash
git clone [https://github.com/your-username/fin-stream-service.git](https://github.com/your-username/fin-stream-service.git)
cd fin-stream-service
docker-compose up -d
