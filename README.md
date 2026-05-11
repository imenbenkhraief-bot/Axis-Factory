# ⚙️ Axis Industrial — Smart Factory Management System

A Java desktop application that simulates an intelligent manufacturing facility. Manage machines, workers, and production shifts through a real-time Swing dashboard — with full MySQL persistence via the DAO pattern.

---

## ✨ Features

- **Production management** — operate five machine types (Welding Robot, Assembly Robot, CNC Machine, Conveyor Belt, Painting Robot) through timed production shifts
- **HR management** — manage workers across three roles (Operator, Technician, Supervisor) with distinct access rights and responsibilities
- **Real-time dashboard** — live KPIs including total parts produced, active machines, available staff, and production rate, refreshed every second via a Swing timer
- **Full persistence** — factory state is saved to and restored from MySQL at every session via CRUD DAOs
- **OOP showcase** — demonstrates abstraction, inheritance (3 levels), polymorphism, encapsulation, Singleton, and DAO patterns in a realistic industrial context

---

## 🏗️ Architecture

The app is structured in **3 strictly separated layers**:

```
┌──────────────────────────────────────┐
│           UI Layer  (ui/)            │
│  MainWindow.java · JTabbedPane       │
│  Dashboard · Production · Personnel  │
├──────────────────────────────────────┤
│       Business Layer  (business/)    │
│  Factory · Machines · Workers        │
│  Interfaces · Abstract classes       │
├──────────────────────────────────────┤
│        Data Layer  (database/)       │
│  DBConnection (Singleton) · JDBC     │
│  MachineDAO · WorkerDAO              │
└──────────────────────────────────────┘
```

### OOP concepts at a glance

| Concept | Implementation |
|---|---|
| Abstraction | `Machine` and `Worker` are abstract — cannot be instantiated directly |
| Interfaces | `IMachine` → `produce()` · `IWorker` → `performDuty()` |
| Inheritance | `Machine` → `Robot` → `AssemblyRobot` (3 levels) |
| Polymorphism | `List<Machine>` iterates all types, calling the correct `produce()` |
| Encapsulation | All attributes are `private`/`protected`, accessed via getters/setters |
| Singleton | `DBConnection.getInstance()` — one shared JDBC connection |
| Static | `Factory.totalPartsProduced` — global counter shared across instances |
| Final | `MAX_CAPACITY` constants, non-overridable methods |

---

## 📂 Project Structure

```
Axis_Factory/
└── src/
    ├── main/
    │   └── Main.java                     # Entry point
    ├── business.factory/
    │   └── Factory.java                  # Core factory logic, totalPartsProduced
    ├── business.interfaces/
    │   ├── IMachine.java                 # produce() contract
    │   └── IWorker.java                  # performDuty() contract
    ├── business.models/
    │   ├── Machine.java                  # Abstract base — id, name, status, partsProduced
    │   └── Worker.java                   # Abstract base — id, name, role, productionLine
    ├── business.models.machines/
    │   ├── AssemblyRobot.java
    │   ├── WeldingRobot.java
    │   ├── CNCMachine.java
    │   ├── ConveyorBelt.java
    │   └── PaintingRobot.java
    ├── business.models.workers/
    │   ├── Operator.java
    │   ├── Technician.java
    │   └── Supervisor.java
    ├── database/
    │   ├── DBConnection.java             # Singleton JDBC connection
    │   ├── MachineDAO.java               # CRUD for machines table
    │   └── WorkerDAO.java                # CRUD for workers table
    └── ui/
        └── MainWindow.java               # JFrame with 3-tab interface
```

---

## 🗄️ Database Setup

### Prerequisites
- [XAMPP](https://www.apachefriends.org/) with MySQL running

### Steps

1. Start **MySQL** in the XAMPP Control Panel.
2. Open phpMyAdmin at `http://localhost/phpmyadmin`.
3. Create a database named `axis_factory_db`.
4. Run the setup script below (or import `database/setup.sql` if included):

```sql
CREATE DATABASE IF NOT EXISTS axis_factory_db;
USE axis_factory_db;

CREATE TABLE IF NOT EXISTS machines (
    id             INT          PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(100) NOT NULL,
    type           VARCHAR(50)  NOT NULL,
    parts_produced INT          DEFAULT 0,
    status         VARCHAR(20)  DEFAULT 'IDLE'
);

CREATE TABLE IF NOT EXISTS workers (
    id              INT          PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    role            VARCHAR(50)  NOT NULL,
    production_line VARCHAR(50)  DEFAULT NULL,
    available       BOOLEAN      DEFAULT TRUE
);
```

---

## ⚙️ Setup & Run

### Prerequisites
- JDK 11+
- Eclipse IDE (recommended)
- XAMPP with MySQL
- `mysql-connector-java.jar` (JDBC driver)

### Steps

1. **Clone the repository** and open the project in Eclipse as an existing Java project.

2. **Add the JDBC driver** to the build path:
   - Right-click the project → `Build Path` → `Add External JARs`
   - Select `mysql-connector-java.jar`

3. Import the `axis_db.sql` file located in the project root.
   ```

4. **Run the database setup** script (see above).

5. **Launch the app** by running `main/Main.java`.

---

## 🖥️ Application Flow

```
Main.java
  └─ DBConnection.getInstance()       ← single JDBC connection established
  └─ MachineDAO.findAll()             ← machines loaded into Factory
  └─ WorkerDAO.findAll()              ← workers loaded into Factory
  └─ MainWindow.setVisible(true)      ← GUI launches

User clicks "Démarrer Shift"
  └─ Factory.runShift()
      └─ machine.produce()            ← polymorphic call per machine type
          └─ totalPartsProduced++     ← global counter incremented
              └─ Timer refreshes Dashboard every second

On close
  └─ MachineDAO.update()             ← state persisted to MySQL
  └─ WorkerDAO.update()
```

---

## 🎓 Academic Context

Developed as part of the **Java Programming** module at **École Polytechnique de Sousse**, 2nd year preparatory cycle (ICT) — academic year **2025–2026**.

**📄 License**
This project is for academic purposes only.

**Developer:** Imen Ben Khraief 
