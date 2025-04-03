# Temelio Grant Workflow API

A Spring Boot backend application to streamline the grant-making process for foundations.

## Overview

This API allows foundations to:

- Create and manage nonprofits with name, address, and email.
- Send bulk templated emails with dynamic fields like `{name}`, `{address}`, and `{date}`.
- View all sent email logs.
- Resend templated emails to a specific nonprofit.

The application uses in-memory storage and mocks email sending functionality.

## Tech Stack

- Java 11+
- Spring Boot
- Maven
- Lombok
- JUnit 5 & Mockito

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven installed

### Run the application

Use the command:  
mvn spring-boot:run

The app will run at:  
http://localhost:8080

### Run tests

Use the command:  
mvn test

## API Endpoints

### Nonprofit

- POST /nonprofits – Create nonprofit  
- GET /nonprofits – List all nonprofits  
- GET /nonprofits/{email} – Get nonprofit by email  


### Emails

- POST /send-emails – Send templated emails  
- GET /emails – View all email logs  
- GET /emails/{email} – View emails for a specific nonprofit  

## Example Email Template

Sending money to nonprofit {name} at address {address} on {date}.

## License

This project is for educational and demonstration purposes only.
