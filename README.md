# 🎓 Course Enrollment Platform

A full-stack Course Enrollment Platform with user and admin roles, secure 2FA authentication, Razorpay payment integration, and a comprehensive admin dashboard to manage the entire website.

## 🚀 Features

### ✅ User Features
- 🔐 Secure Authentication with Two-Factor Authentication (2FA)
- 📚 Browse and purchase online courses
- 💳 Payment integration using Razorpay
- 🧾 View purchase history and enrolled courses
- 🧑‍🎓 Role-based access (Student / Admin)

### ✅ Admin Features
- 📋 Dashboard to view platform analytics
- 🧑‍💻 Manage users and their roles
- 📚 Add, update, and delete courses
- 💰 View payments and revenue reports
- 🔔 Send announcements or notifications to users

---

## 🔧 Tech Stack

### Frontend (React.js)
- React with Hooks and Context API
- React Router for routing
- Axios for API calls
- TailwindCSS / Bootstrap for styling
- JWT for session handling

### Backend (Spring Boot)
- Spring Boot REST APIs
- Spring Security with Role-Based Authorization
- 2FA with OTP (Email or SMS)
- Razorpay Integration for secure payments
- MySQL for relational data storage
- JPA/Hibernate for ORM

---

## 📂 Folder Structure
📦course-enrollment-platform
├── 📁backend (Spring Boot)
│ ├── controller
│ ├── service
│ ├── repository
│ ├── entity
│ └── config
├── 📁frontend (React.js)
│ ├── components
│ ├── pages
│ ├── routes
│ ├── services
│ └── utils
### 1. Clone the Repository
```bash
git clone https://github.com/your-username/course-enrollment-platform.git
cd course-enrollment-platform
Backend setup
------------------------------
cd backend
# Configure application.properties with DB credentials and Razorpay keys
mvn clean install
mvn spring-boot:run
Fontend SetUp
-------------------
cd frontend
npm install
npm start
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_jwt_secret
razorpay.key_id=your_key_id
razorpay.key_secret=your_key_secret

