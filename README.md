# Sensor Calibration Management System

**Anna University Regulation 2021 — CCS342 DevOps Assignment**  
**Student Name:** KALAIVARSAN V  
**Roll No / Topic ID:** 25  
**Course Code & Name:** CCS342 — DevOps  

---

## 📌 Project Overview
The **Sensor Calibration Management System** is an automated DevOps-driven microservice application built to manage industrial IoT sensors, calculate calibration correction factors, track sensor drift tolerances, and maintain auditable calibration logs.

This repository demonstrates complete end-to-end integration across all five units of the **CCS342 DevOps** curriculum:
1. **Unit I (DevOps & SCM)**: Version Control with Git & GitHub branching strategy.
2. **Unit II (Build Automation)**: Maven build lifecycle, `pom.xml` dependency management, and automated JUnit test execution.
3. **Unit III (Continuous Integration)**: Jenkins Master-Agent automated build pipeline (`Jenkinsfile`), test publishing, and container image generation.
4. **Unit IV (Configuration Management)**: Ansible playbooks (`deploy.yml`) and inventory automation for zero-downtime server deployments.
5. **Unit V (Cloud CI/CD Pipelines)**: Azure DevOps Multi-stage Pipeline (`azure-pipelines.yml`) for automated cloud deployment and verification.

---

## 🛠️ Technology Stack
* **Programming Language**: Java 17 (LTS)
* **Framework**: Spring Boot 3.2.2 (REST API & Actuator)
* **Build Tool**: Apache Maven 3.9+
* **Continuous Integration**: Jenkins CI / Azure Pipelines
* **Containerization**: Docker (Multi-stage build) & Docker Compose
* **Configuration Management**: Ansible 2.15+
* **Testing Framework**: JUnit 5 & Spring Boot Starter Test

---

## ⚙️ How to Upload and Push to GitHub

To push this repository to your own GitHub profile, open your terminal inside the project directory and run:

```bash
# 1. Initialize local Git repository
git init

# 2. Stage all project files
git add .

# 3. Create initial commit
git commit -m "feat: complete sensor calibration management system with CI/CD pipeline"

# 4. Rename default branch to main
git branch -M main

# 5. Link to your GitHub repository (replace with your repo URL)
git remote add origin https://github.com/<your-github-username>/sensor-calibration-management-system.git

# 6. Push code to GitHub
git push -u origin main
```

---

## 🚀 Step-by-Step Local Execution

### 1. Build and Run Unit Tests with Maven
```bash
# Compile and run test suite
mvn clean test

# Package Spring Boot executable JAR
mvn clean package -DskipTests

# Run application
java -jar target/sensor-calibration-system-1.0.0.jar
```

### 2. Run with Docker
```bash
# Build Docker image
docker build -t sensor-calibration-mgmt:1.0 .

# Run container
docker run -d -p 8080:8080 --name sensor-app sensor-calibration-mgmt:1.0

# Verify health status
curl http://localhost:8080/api/health
```

### 3. Deploy via Ansible
```bash
ansible-playbook -i devops/ansible/hosts.ini devops/ansible/deploy.yml
```

---

## 📡 REST API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/health` | Service health status and student metadata |
| `GET` | `/api/sensors` | List all industrial registered sensors |
| `POST` | `/api/sensors` | Register a new sensor with tolerance limit |
| `POST` | `/api/sensors/{id}/calibrate` | Calculate drift percentage and calibration factor |
| `GET` | `/api/sensors/{id}/records` | Fetch historical calibration audit trail |

### Sample Calibration Request
```bash
curl -X POST http://localhost:8080/api/sensors/SN-TEMP-101/calibrate \
  -H "Content-Type: application/json" \
  -d '{
    "referenceValue": 100.0,
    "measuredValue": 101.2,
    "technician": "Kalaivarsan V"
  }'
```

### Sample Response
```json
{
  "recordId": "CAL-9E4B2F10",
  "sensorId": "SN-TEMP-101",
  "referenceStandardValue": 100.0,
  "measuredSensorValue": 101.2,
  "calibrationFactor": 0.9881,
  "driftPercentage": 1.2,
  "calibrationStatus": "PASS",
  "technicianName": "Kalaivarsan V",
  "calibrationTimestamp": "2026-11-10T23:30:00"
}
```
