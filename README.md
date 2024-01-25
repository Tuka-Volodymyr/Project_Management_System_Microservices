# Project Management System on Java Spring Boot with Microservices Architecture

## Overview

The Project Management System is a robust web application built on Java Spring Boot, utilizing a microservices architecture. The system is designed to efficiently manage projects, tasks, teams, and user identities, incorporating security through JWT (JSON Web Token) tokens.

## Microservices

 **1. Eureka Server (server registry)**

- Description: Acts as a service registry, registering and managing all microservices within the system.

 **2. Gateway Service**

- Description: Serves as the gateway for external requests, routing them to the appropriate microservices.

 **3. Identity Service**

- Description: Handles user identity and authentication, ensuring the security of the system.
- Security: Utilizes JWT tokens for secure communication.

 **4. Project Service**

- Description: Manages the creation, modification, and retrieval of projects.

 **5. Task Service**

- Description: Handles tasks related to projects, allowing for efficient task management.

 **6. Team Service**

- Description: Facilitates the creation and management of teams associated with projects.

## Security

**JWT Tokens:**
  - All communication within the microservices is secured using JSON Web Tokens.
  - The Identity Service ensures user authentication and token generation.

## How to Run

1. Start the Eureka Server (Server Registry).
2. Launch the Gateway Service.
3. Initiate the Identity Service for user authentication.
4. Deploy the Project Service for project management.
5. Activate the Task Service for task-related functionalities.
6. Run the Team Service for team management.
   
## Conclusion

The Project Management System leverages the advantages of microservices to provide a scalable, modular, and secure solution for effective project management. The use of JWT tokens enhances the security of the system, ensuring a reliable and efficient platform for managing projects, tasks, and teams.
