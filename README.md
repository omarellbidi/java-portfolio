# Advanced Banking System

## Overview
This project implements a comprehensive enterprise-grade banking system in Java, showcasing object-oriented programming principles, clean architecture, and database persistence. The system manages different types of accounts (Personal and Corporate), customers, financial transactions, and maintains a complete transaction history.

## Features
- Customer management with unique customer IDs
- Support for different account types (Personal and Corporate)
- Secure transaction operations (deposit, withdraw, transfer) with ACID properties
- Balance management with appropriate validation
- Financial calculations using BigDecimal for accuracy
- Persistent data storage with MySQL database
- Transaction history and account statements
- Audit logging for security
- Connection pooling for scalability

## Technical Implementation
This implementation demonstrates:
- Clean layered architecture (domain, data access, service layers)
- Design patterns (DAO, Singleton, Factory)
- Database integration with JDBC
- Transaction management and connection pooling
- Logging with SLF4J and Logback
- Maven for dependency management and build automation

## Key Operations
- Register and manage customers
- Create personal and corporate accounts
- Make deposits and withdrawals with transaction safety
- Transfer funds between accounts
- Generate transaction history and account statements
- Calculate total balances
- Maintain security audit logs

## Technologies Used
- Java
- MySQL Database
- JDBC
- Connection Pooling
- Maven
- SLF4J and Logback for logging
- Object-Oriented Design principles
- BigDecimal for precise financial calculations
- Java Collections Framework

