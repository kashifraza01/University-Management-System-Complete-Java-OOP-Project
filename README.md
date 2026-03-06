# 📌 University Management System

A complete **Java Swing + MySQL** based desktop application designed to manage university academic and administrative operations efficiently.

---

## 📖 Overview

The **University Management System (UMS)** is a GUI-based desktop application developed using **Java (Swing)** and **MySQL**.
It automates student records, examination management, and semester fee tracking while enforcing proper validation rules to prevent duplicate entries and invalid transactions.

This project demonstrates practical implementation of **DBMS concepts, relational database design, JDBC connectivity, and backend validation logic**.

---

## 🚀 Features

### 👨‍🎓 Student Management

* Add new students with dynamic semester & campus selection
* Update existing student records
* Maintain structured student database

### 👨‍🏫 Teacher Management

* Add and update teacher records
* Subject assignment management

### 📝 Examination & Marks System

* Enter semester-wise marks
* Prevent duplicate marks entry
* Display only eligible semesters based on enrolled semester
* View detailed examination results

### 💰 Fee Management System

* Semester-wise fee tracking
* Half-payment functionality
* Automatic status updates (Paid / Half Paid / Unpaid)
* Back-payment restriction (Cannot pay next semester before clearing previous dues)

### 🔐 Authentication System

* Secure login for system access

---

## 🛠️ Technologies Used

* **Java (Swing)** – GUI Development
* **MySQL** – Relational Database
* **JDBC** – Database Connectivity
* **SQL** – Queries, Constraints, Schema Design

---

## 🗄️ Database Design

The system uses a structured relational schema including tables such as:

* `login`
* `student`
* `teacher`
* `subject`
* `marks`
* `fee`
* `college_fee`

Database normalization principles and foreign key relationships were implemented to maintain data integrity.

---

## 🧠 Key Concepts Applied

* CRUD Operations
* Relational Database Design
* Primary & Foreign Keys
* Data Validation & Business Logic Handling
* Dynamic Data Fetching from Database
* Semester Validation Logic
* JDBC Integration

---

## 📷 Screenshots

```
![Dashboard Screenshot](1.png)
![Marks Entry Screenshot](screenshot/2.png)
![About Me Screenshot](screenshot/5.png)
![About Me Screenshot](5.png)
![About Me Screenshot](https://github.com/kashifraza01/University-Management-System-Complete-Java-OOP-Project/blob/2fb1fac899347b90c8ffc4bdae68cca932d08d26/1.png)
```

---

## ▶️ How to Run

1. Clone the repository

```bash
git clone https://github.com/your-username/university-management-system.git
```

2. Import project into **IntelliJ IDEA / NetBeans / Eclipse**
3. Create the MySQL database
4. Import the provided `.sql` file
5. Configure database credentials in the connection file
6. Run the main Java file

---

## 🎯 Learning Outcome

This project strengthened my understanding of:

* Real-world DBMS implementation
* Desktop application architecture
* Backend validation logic
* Database-driven application development

---

## 👨‍💻 Author

**Kashif Raza**
BS Computer Science Student
