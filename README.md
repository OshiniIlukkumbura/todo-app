# 📝 Todo Application

A simple Dockerized Todo Task Web Application that allows users to add tasks, mark them as completed, and view the latest 5 tasks.

## Tech Stack

- **Frontend:** React, TypeScript, MUI, React Router. Jest
- **Backend:** Spring Boot 3, Java 17, REST API with JWT Authentication, JUnit 5, Mockito
- **Database:** MySQL 8
- **Containerization:** Docker, Docker Compose

---

## Features

- User authentication using **JWT tokens**.
- Add a new todo task (title and description).
- Mark tasks as completed — completed tasks disappear from the UI.
- Only the 5 most recent incomplete tasks are displayed.
- Simple Single Page Application(SPA) interface.

---

## Requirements

- Docker
- Docker Compose
- Git for cloning the repository

---

## Setup & Run

1. **Clone the repository**

git clone <your-repo-url>
cd todo-app

2. **Start the application using Docker Compose**

docker-compose up --build

3. **Access the app**

Frontend UI: http://localhost:3000

Backend API: http://localhost:8088

The backend waits for the database to be ready before starting.

---

## Docker Compose Overview

- db → MySQL 8 (database for tasks)  
- backend → Spring Boot REST API with JWT authentication  
- frontend → React SPA served via Nginx  

**Example container ports:**

Service Port:-  
DB -> 3306  
Backend ->  8088  
Frontend -> 3000  

**Project Structure**

/backend → Spring Boot API  
/frontend → React SPA  
/docker-compose.yml  
/wait-for-it.sh  

---

## Usage

1. Open the frontend UI.
2. Login or register to receive a JWT token.
3. Add a new task using the form.
4. Click Done on a task to mark it as completed.
5. Only the 5 latest incomplete tasks are shown.
6. Use Logout to clear your JWT token and return to the login page.
