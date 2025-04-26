# 🛠️ Xpert – Expert Hiring Platform

**Xpert** is a scalable, modular, and secure **expert hiring web platform** (like Fiverr) built using **Spring Boot 3.4.4**. It enables users to hire professionals (electricians, carpenters, doctors, consultants, etc.) for both **offline and online services**.


---

## 🚀 Features

### 🔐 User & Profile Management
- AES-GCM encrypted `email` and `phone` fields using `@Convert` and a custom `EncryptionUtil`.
- Roles and statuses managed via enums (`CUSTOMER`, `XPERT`, `ADMIN`).
- Full profile system with image, CV, experience description, and notifications.

### 📍 Address Management
- Users can manage multiple addresses with soft deletion.
- Supports marking default addresses.
- Clean DTO mapping and validation using `jakarta.validation`.

### 💬 Chat System
- Real-time chat entity with participants and message history.
- Support for message attachments with `ChatAttachment`.

### 📅 Booking & Order System
- Users can schedule services (orders) with experts.
- Supports `OrderStatus` with full lifecycle tracking (`SCHEDULED`, `IN_PROGRESS`, `COMPLETED`).

### 🧾 Invoicing
- Invoices tied to orders, with encrypted reference IDs.
- Implements binary UUID optimization (`BINARY(16)`).
- Integrated with **JPA Auditing** (`@CreatedDate`, `@LastModifiedDate`) for `issuedAt`, `createdAt`, `updatedAt`.

### 🌟 Review System
- Clients can review experts once per order.
- Review approval status is controlled by the admin.

### ⚙️ Admin Tools (in progress)
- Admins can manage categories and service types.
- Role-based restrictions using Spring Security (JWT to be implemented).

---

## 🧱 Tech Stack

| Layer | Technology |
|-------|------------|
| Backend Framework | Spring Boot 3.4.4 |
| ORM | Spring Data JPA (Hibernate) |
| Encryption | AES-GCM (Javax Crypto + Base64) |
| Validation | Jakarta Validation (Bean Validation 3.0) |
| Database | MySQL |
| Documentation | Swagger/OpenAPI (in progress) |
| Testing | JUnit 5, Spring Boot Test |
| Code Quality | SonarCloud + GitHub CodeQL |

---

## 🔐 Security & Best Practices

✅ **Field-Level AES Encryption:** `email`, `phone`, `referenceId`  
✅ **Entity UUID Optimization:** `@Column(columnDefinition = "BINARY(16)")`  
✅ **Validation:** DTOs annotated with `@NotNull`, `@Size`, `@Future`, etc.  
✅ **Auditing:** `@CreatedDate`, `@LastModifiedDate` via `@EnableJpaAuditing`  
✅ **Centralized Exception Handling:** Implemented with structured JSON output and `errorId` traceability  
✅ **Constructor Injection:** Using `@RequiredArgsConstructor` with `final` fields  
✅ **SonarCloud & CodeQL Compliance:** All modules pass Quality Gate  

---

