# 📚 Library Management System

A secure, extensible backend API built with **Java, Spring Boot, and MySQL**. It enables administrators, librarians, and staff to manage books, members, borrowing transactions, book copies, and system users with role-based access control.  

---

## 🚀 Technology Stack  

- **Java 21** – Modern LTS version, strong typing, stability  
- **Spring Boot 3.x** – Rapid application setup, embedded server, auto-configuration  
- **Spring Data JPA (Hibernate)** – Simplifies ORM and database interactions  
- **Spring Security** – HTTP Basic authentication with role-based authorization  
- **MySQL 8** – Relational database for reliable data persistence  
- **Lombok** – Reduces boilerplate in entity classes  
- **Maven** – Dependency management and build lifecycle  

---

## 🏗️ Design Choices  

### 1️⃣ Layered Architecture  
- **Controllers** – Handle HTTP requests and map to service calls  
- **Services** – Contain business logic (borrowing limits, return process)  
- **Repositories** – Use Spring Data JPA for CRUD operations  
- **Entities** – Annotated with JPA to define table mappings and relationships  

### 2️⃣ Entity Relationships  
- **Book ↔ Author** – Many-to-many via join table  
- **Book ↔ Category** – Many-to-many with hierarchical categories  
- **Book ↔ Publisher** – Many-to-one for publisher metadata  
- **Book ↔ BookCopy** – One-to-many to track physical copies  
- **Member ↔ BorrowingTransaction** – One-to-many for borrowing history  
- **SystemUser** – Single table with role enum: ADMIN, LIBRARIAN, STAFF, MEMBER  

### 3️⃣ Security & Authentication  
- BCryptPasswordEncoder for secure password storage  
- CustomUserDetailsService to load user credentials from DB  
- Role-based Authorization protecting endpoints  
- HTTP Basic authentication for simplicity  

### 4️⃣ Borrowing & BookCopy Logic  
- **Max 3 books per member** enforced in service/controller  
- **Single-copy enforcement** – Prevent borrowing if returnDate is NULL on active transaction  
- **Return endpoint** marks returnDate to free the book  
- **BookCopy management** – Add and manage physical copies of each book  

### 5️⃣ Logging & Auditing  
- Console logs for registration, login

---

## 📡 API Endpoints  

### 🔐 Users (CRUD)  
Manage system users:  

- `POST /api/users/createUsers` — Create new user (ADMIN only)  
- `GET /api/users` — List all users (ADMIN only)  
- `GET /api/users/{id}` — Get user by ID (ADMIN only)  
- `PUT /api/users/{id}` — Update user (ADMIN only)  
- `DELETE /api/users/{id}` — Delete user (ADMIN only)  

---

### 📚 Books  
- `GET /api/books` — List all books  
- `GET /api/books/{id}` — Get book by ID  
- `POST /api/books` — Create book (LIBRARIAN, ADMIN)  
- `PUT /api/books/{id}` — Update book (LIBRARIAN, ADMIN)  
- `DELETE /api/books/{id}` — Delete book (ADMIN)  

---

### 🗂️ Book Copies  
Manage physical copies of books:  

- `POST /api/book-copies?bookId={bookId}&barcode={barcode}` — Create a new copy for a book  
- `GET /api/book-copies/book/{bookId}` — Get all copies of a book  
- `PUT /api/book-copies/{copyId}/status?status=AVAILABLE|BORROWED` — Update status of a copy  

---

### 👥 Members  
- `GET /api/members` — List members (LIBRARIAN, ADMIN)  
- `GET /api/members/{id}` — Get member by ID  
- `POST /api/members` — Create member  
- `PUT /api/members/{id}` — Update member  
- `DELETE /api/members/{id}` — Delete member  

---

### 🔄 Borrowings  
- `POST /api/borrowings?memberId=&bookCopyId=` — Borrow a book copy  
- `POST /api/borrowings/return?memberId=&bookCopyId=` — Return a book copy  
- `GET /api/borrowings` — List all borrowings  
- `GET /api/borrowings/{id}` — Get transaction by ID  


