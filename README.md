<div align="center">

# Xpert

**Empowering you to find—and become—the world’s top experts, online or offline.**

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Learnathon-By-Geeky-Solutions_bytebuilders-&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Learnathon-By-Geeky-Solutions_bytebuilders-)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Learnathon-By-Geeky-Solutions_bytebuilders-&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Learnathon-By-Geeky-Solutions_bytebuilders-)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Learnathon-By-Geeky-Solutions_bytebuilders-&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Learnathon-By-Geeky-Solutions_bytebuilders-)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Learnathon-By-Geeky-Solutions_bytebuilders-&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Learnathon-By-Geeky-Solutions_bytebuilders-)

</div>

---

## Table of Contents

- [Overview](#overview)  
- [Key Features](#key-features)  
- [Tech Stack and Architecture](#tech-stack-and-architecture)  
- [Architecture Diagrams](#architecture-diagrams)  
- [API Reference](#api-reference)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Installation](#installation)  
  - [Build](#build)  
  - [Run](#run)  
  - [Docker Setup](#docker-setup)  
- [License](#license)  

---

## Overview

**Xpert** is a Spring Boot–powered freelance platform built to connect clients with professionals—electricians, carpenters, doctors, consultants, and beyond—whether they need in-person service or online advice.  
By providing rich features, Xpert streamlines the entire lifecycle:

1. **Discover** experts via category, location, ratings, or AI match  
2. **Book** services (single work unit or multi-unit agreements)  
3. **Pay** using escrow-style invoices and on-demand payout  
4. **Review** and **repeat** with one click  

---

## Key Features

1. **User & Expert Management**  
   - Registration & verification: Email/phone OTP + AES-encrypted personal data  
   - JWT-secured authentication with role-based access (CLIENT, EXPERT, ADMIN)  
   - Profile builder: Photos, CV upload, short bio, portfolio links  
   - Device login history & active session control  

2. **Service Catalog & Scheduling**  
   - Categories & subcategories with images & SEO metadata  
   - Service listings: Title, description, pricing, durations  
   - Work unit bundles: Break large agreements into billable units  
   - Real-time availability & calendar integration (Google Calendar, Outlook)  
   - Geolocation search: Find experts near you + map view  

3. **End-to-End Booking & Agreements**  
   - Agreement builder: Draft multi-unit contracts, time estimates, milestones  
   - Automated reminders & rescheduling  
   - In-app chat & video calls (WebRTC) with file attachments  
   - Escrow payments: Funds held until milestone completion  

4. **Payments, Invoices & Payouts**  
   - Stripe integration  
   - Multi-step invoicing & PDF export  
   - Partial release / refund workflows  
   - Expert payout dashboard: Bank integrations, scheduled withdrawals  

5. **Ratings, Reviews & Trust**  
   - 5-star ratings with written reviews  
   - Expert badges: Top rated, verified, industry specialists  
   - Dispute resolution & admin moderation tools  

6. **Admin & Analytics**  
   - Role-based admin panel: User, service, transaction management  
   - Real-time dashboard: Bookings, revenue, active users  
   - Custom reports: CSV/PDF export, email scheduling  
   - Audit trails & GDPR-compliant data exports  

7. **Advanced & Future-Ready**  
   - AI-powered matchmaking: Skill-to-need recommendations  
   - Loyalty & referral programs with tiered rewards  
   - Mobile-first PWA & native app support  
   - Multi-language / multi-currency for global growth  
   - Webhooks & event-driven microservices (RabbitMQ / Kafka)  

---

## Tech Stack and Architecture

- **Backend**: Spring Boot, Spring Security (JWT), Spring Data JPA  
- **Database**: PostgreSQL  
- **CI/CD & Quality**: GitHub Actions, SonarCloud, CodeQL, JaCoCo  
- **Deployment**: Docker  

---

## Architecture Diagrams

### Entity Relationship Diagram  
![ER Diagram](images/er-diagram.png)

### System Flow Diagram  
![System Flow](images/system-flow-diagram.png)

---

## API Reference

| Controller                 | Base Path                 | Methods                                                           |
|----------------------------|---------------------------|-------------------------------------------------------------------|
| AuthenticationController   | `/api/auth`               | `POST /login`, `POST /register`                                  |
| AdminUserController        | `/api/admin/users`        | `GET /`, `GET /{userId}`, `PATCH /{userId}/status`               |
| CategoryController         | `/api/categories`         | `POST /`, `GET /`, `GET /{id}`                                    |
| AgreementController        | `/api/agreements`         | `POST /`, `GET /{id}`, `GET /?page=&size=`                        |
| OrderController            | `/api/orders`             | `POST /`, `GET /{id}`, `GET /?page=&size=`                        |
| PaymentController          | `/api/payments`           | `POST /orders/{orderId}`, `GET /{id}`, `GET /?orderId=`, `GET /` |
| InvoiceController          | `/api/invoices`           | `POST /`, `GET /{id}`, `GET /?orderId=`                           |
| ReviewController           | `/api/reviews`            | `POST /`, `GET /{orderId}`, `PATCH /{reviewId}/approve`           |
| ChatController             | `/api/chats`              | `POST /`, `GET /{chatId}`, `GET /`                               |
| MessageController          | `/api/messages`           | `POST /`, `GET /{messageId}`, `GET /?chatId=`                     |
| UserProfileController      | `/api/user-profiles`      | `GET /{userId}`, `PUT /{userId}`                                  |
| UserAddressController      | `/api/user-addresses`     | `POST /`, `GET /{id}`, `GET /?userId=`, `PATCH /{id}/default`     |
| LocationController         | `/api/locations`          | `GET /{id}`                                                      |
| WorkUnitController         | `/api/work-units`         | `POST /`, `GET /{id}`, `GET /`                                   |

_Add JWT `Authorization: Bearer <token>` header where required._

---

## Getting Started

### Prerequisites

- Java 17+ JDK  
- Maven 3.8+  
- PostgreSQL or MySQL 13+  
- Docker & Docker Compose (optional)  

### Installation

```bash
git clone https://github.com/Learnathon-By-Geeky-Solutions/bytebuilders-.git
cd xpert
```

### Build

```bash
mvn clean package -DskipTests
```

### Run

```bash
java -jar target/xpert.jar --spring.profiles.active=dev
```

### Docker Setup

```bash
docker-compose up -d
```

---

## License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

<div align="center">  
Made with ❤️ by the Xpert Core Team  
</div>
