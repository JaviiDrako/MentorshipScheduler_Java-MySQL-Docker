# 🎓 MentorshipScheduler – Academic Tutoring Scheduler System 📅

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-red?logo=java" alt="Java Badge" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql" alt="MySQL Badge" />
  <img src="https://img.shields.io/badge/Docker-Engine-2496ED?logo=docker&logoColor=white" alt="Docker Badge" />
  <img src="https://img.shields.io/badge/Maven-Build%20Tool-C71A36?logo=apachemaven&logoColor=white" alt="Maven Badge" />
</p>


**MentorshipScheduler** is a **console-based Java application** designed to help schedule academic mentoring sessions between students and tutors. The system is ideal for educational environments such as **universities, colleges, or schools**, where students can request support in various subjects and tutors can offer their availability.

This application provides a **flexible and robust system** for managing, scheduling, and tracking academic tutoring sessions using **Java 17**, **MySQL**, and **Docker**.

---

## 💡 Project Features

### 🔁 Flexible Subject Selection  
Students can freely choose the **subject** for each mentorship session (e.g., Math, Programming, Chemistry), without preassigned restrictions.

### 🧑‍🏫 Tutor-Controlled Session Creation  
Tutors can **create available mentorship slots**, while the system automatically **ensures no scheduling conflicts** occur for that tutor.

### ❌ Session Cancellation  
Both **students and tutors** can cancel sessions:
- If a **tutor** cancels, the session becomes to **Canceled** state from the system.
- If a **student** cancels, the session is **freed up** so another student may reserve it.

### 📆 Availability Validation  
MentorshipScheduler checks **real-time availability** of tutors based on the stored data in the MySQL database.

### 📋 Session History  
Users can view their **scheduled or taught sessions**, filtered by **subject, date, or session status** (active/cancelled).

---

## ⚙️ Technologies Used

- 💻 **Java 17+** – Main programming language.
- 🐬 **MySQL 8** – Relational database.
- 🐳 **Docker** – Containerized MySQL setup.
- 📦 **Maven** – Build tool and dependency manager.

---
## 🧱 Architecture

MentorshipScheduler follows a **layered architecture inspired by the MVC (Model-View-Controller) pattern**, adapted for a console-based environment.

- **Models (`models/`)**  
  Represent domain entities like `Student` and `Tutor`, with their respective attributes and basic behavior.

- **Data Access Layer (`crud/`)**  
  This layer interacts with the MySQL database using DAO (Data Access Object) classes. It manages:
  - Opening and closing connections (`DBConnectionManager`)
  - Inserting, updating, deleting and fetching students and tutors (`StudentDAO`, `TutorDAO`, `UserDAO`)

- **Menus / View Layer (`menus/`)**  
  These classes (`StudentMenu`, `TutorMenu`) handle user interaction via the terminal.  
  They present options and capture input from users, acting as the "View" in the MVC pattern.

- **Main Controller (`Main.java`)**  
  Serves as the entry point of the application. It initializes the app, presents the main menu, and routes the logic to the appropriate menu handlers.

This modular organization makes the codebase easier to maintain, test, and extend with new features such as additional user roles or GUI interfaces in the future.

---

## 🚀 How to Run the Project

### 1. 📦 Compile the Java project

Open a terminal and navigate to the project root:

```bash
mvn clean package
```

This will generate the .jar file inside the target/ folder.

### 2. 🐳 Start the MySQL database using Docker

Make sure you have **Docker** installed on your machine.  
If you don’t have it yet, you can install it by following the official Docker installation guide:  
👉 [Install Docker](https://docs.docker.com/engine/install/)

From the **root of the cloned project**, run the following command to start the MySQL container with the preconfigured database and credentials:

```bash
sudo docker compose up
```
This will:

- Start a MySQL container on port 3307.
- Create a database called MentorshipsDB.
- Run the initial SQL script to populate data (students, tutors, subjects, mentorships, etc.).

You can customize database credentials via `compose.yml` and then also changing them in `DBConnectionManager` class.

### 3. ▶️ Run the application
Once the database is running and ready, execute:

```bash
java -jar target/mentorships-app-1.0-SNAPSHOT.jar
```
This will launch the interactive console application, where you can register users, schedule sessions, log in, cancel mentorships, and explore the full CRUD system.

--- 

## 📝 Notes
You can change the port (3307) or DB credentials in compose.yml.

If you modify credentials or structure, make sure to remove the existing Docker volume with:

```bash
sudo docker volume rm mentorships_mysql_data
```
This ensures the database is reinitialized with your changes.

---

## ⭐ Support the Project

If you found this project helpful or inspiring, feel free to give it a ⭐ star on GitHub — it really helps!

Your feedback and support are always appreciated.  
Happy coding and good luck scheduling your mentorships! 😊






