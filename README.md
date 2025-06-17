# Municipal IT Division CMS

## 📌 Project Overview

This project is a **web-based Complaint Management System (CMS)** designed for the Municipal IT Division.  
It allows users to **register, login, and submit complaints**.  
Administrators can view all complaints, resolve, or delete them through a **secure administrative dashboard**.

### ✨ Features
- User registration and login with role-based authentication
- Submit complaints (Employee role)
- View and delete own complaints (Employee role)
- View all complaints and resolve or delete complaints (Administrator role)
- SweetAlert2 integrated for user notifications
- Built with **JSP, Servlets, MySQL, Apache Tomcat**

---

## 🛠 Setup and Configuration Guide

### 📋 Prerequisites
- **Java 11 or higher**
- **Apache Tomcat 9 or later**
- **MySQL 8+**
- **Maven** (optional, if you want to build with maven)
- **IDE:** IntelliJ IDEA, Eclipse, or NetBeans

---

### 🔧 Installation and Deployment

1️⃣ **Clone the repository:**
```bash
git clone https://github.com/Anju-hesh/CMS-----Java-EE-Assignment.git
cd CMS-----Java-EE-Assignment

mysql -u root -p < db/schema.sql


