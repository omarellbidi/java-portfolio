# Java Banking System

## Overview
This project implements a comprehensive banking system in Java, showcasing object-oriented programming principles and clean architecture. The system manages different types of accounts (Personal and Corporate), customers, and financial transactions.

## Features
- Customer management with unique customer IDs
- Support for different account types (Personal and Corporate)
- Secure transaction operations (deposit, withdraw, transfer)
- Balance management with appropriate validation
- Financial calculations using BigDecimal for accuracy

## Technical Implementation
This implementation demonstrates:
- Inheritance and polymorphism (Account hierarchy)
- Encapsulation of data and operations
- Clean separation of concerns
- Input validation and error handling
- Use of Java Collections Framework
- Precise financial calculations

## Class Structure
- **Bank**: Main class that handles banking operations
- **Account**: Abstract base class for all account types
- **PersonalAccount**: Account for individual customers (no overdraft)
- **CorporateAccount**: Business account (allows negative balance)
- **Customer**: Represents bank customers with personal information

## Key Operations
- Register customers
- Create personal and corporate accounts
- Make deposits and withdrawals
- Transfer funds between accounts
- Calculate total balances

## Technologies Used
- Java
- Object-Oriented Design principles
- BigDecimal for precise financial calculations
- Java Collections Framework

## Development
This project was developed as part of Software Engineering coursework at the University of Salzburg, focusing on object-oriented design principles and clean code practices.