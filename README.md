# CMS - Java EE Assignment

## Project Overview
This is a basic Content Management System (CMS) built using Java Servlets and JSP.  
It supports user authentication, session management, and content CRUD operations.  
The system allows users to create, update, delete, and view content easily through a web interface.  
This project is designed as a simple Java EE web application demonstrating core servlet and JSP features.

## Features
- User login and logout with session handling
- Create, read, update, and delete complaints
- Clean and simple user interface using JSP and Servlet technologies
- Session management to protect user data and authentication

## Setup and Configuration Guide
1. Clone the repository:
   ```bash
   git clone https://github.com/Anju-hesh/CMS-----Java-EE-Assignment.git

2. Import the project into your favorite IDE (e.g., IntelliJ IDEA, Eclipse)  
3. Configure your servlet container (e.g., Apache Tomcat)  
4. Build and run the project
5. Access the app via `[http://localhost:8080/your-app-name](http://localhost:8080/Complain_Managment_System_Web_exploded/)`

FILE STRUCTURE ....

 IK-ijse.gase72/
├── src/
│   └── main/
│       ├── java/
│       │   └── lk/
│       │       └── ijse/
│       │           └── gdse72/
│       │               ├── controller/
│       │               │   ├── AdminViewComplaintServlet.java
│       │               │   ├── ComplaintDeleteServlet.java
│       │               │   ├── ComplaintEditServlet.java
│       │               │   ├── ComplaintStatusUpdateServlet.java
│       │               │   ├── ComplaintSubmitServlet.java
│       │               │   ├── LoginServlet.java
│       │               │   ├── LogoutServlet.java
│       │               │   ├── MyComplaintsServlet.java
│       │               │   └── RegisterServlet.java
│       │               ├── filter/
│       │               │   ├── AuthenticationFilter.java
│       │               │   └── CROSFilter.java
│       │               ├── model/
│       │               │   ├── podos/
│       │               │   ├── ComplaintDTO.java
│       │               │   ├── UserDTO.java
│       │               │   ├── ComplaintDAO.java
│       │               │   └── UserDAO.java
│       │               └── util/
│       │                   ├── DatabaseConfig.java
│       │                   ├── IdGenerator.java
│       │                   └── ValidationUtil.java
│       └── resources/
├── test/
├── target/
├── web/
│   ├── css/
│   │   ├── myComplaint.css
│   │   ├── style.css
│   │   └── viewAll.css
│   ├── js/
│   │   └── validation.js
│   ├── view/
│   │   ├── admin-dashboard.jsp
│   │   ├── complaint-form.jsp
│   │   ├── edit-complaint.jsp
│   │   ├── employee-dashboard.jsp
│   │   ├── login.jsp
│   │   ├── my-complaints.jsp
│   │   ├── register.jsp
│   │   └── view-all-complaints.jsp
│   └── WEB-INF/
│       └── web.xml
├── index.jsp
├── .gitignore
├── pom.xml
└── Complain_Management_System.iml
