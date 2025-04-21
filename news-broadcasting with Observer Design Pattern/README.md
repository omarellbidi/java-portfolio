# News Broadcasting System (Observer Pattern Implementation)

## Overview
This project implements a news broadcasting application using the Observer Design Pattern. The system allows trusted sources to publish news that is then distributed to all registered receivers. The architecture demonstrates effective decoupling of components through the Observer pattern.

## Features
- Secure source registration with password protection
- Real-time news broadcasting to multiple receivers
- Timestamp recording for all news items
- Source attribution for all broadcasts
- Topic-based subscription filtering system
- Password security with hashing (not plain-text storage)

## Technical Implementation
This implementation demonstrates:
- Observer Design Pattern (Subject-Observer relationship)
- Push-based notification system
- Component decoupling for maintainable architecture
- Clean separation of concerns with appropriate packages
- Secure password management

## Components
- **NewsBroadcaster**: The central Subject implementation that manages sources and observers
- **NewsSource**: Interface for components that publish news
- **NewsReceiver**: Observer implementation that receives and processes news
- **ConsoleNewsSource**: Command-line interface for news input
- **FileNewsReceiver**: Writes received news to a file
- **ConsoleNewsReceiver**: Displays news in the console

## Design Pattern Benefits
- Low coupling between news sources and receivers
- Scalable architecture (easy to add new sources or receivers)
- Flexible subscription model with topic filtering
- Centralized broadcasting logic with distributed receiving

## Technologies Used
- Java
- Object-Oriented Design principles
- Observer Design Pattern
- Password hashing for security

## Development
This project was developed as part of Software Engineering coursework at the University of Salzburg, focusing on design patterns and software architecture principles.