# Composite Pattern Item Management System

## Overview
This project implements a hierarchical item management system using the Composite Design Pattern. It can parse XML data to create a tree structure of items (books, CDs) and item collections, enabling operations to be performed uniformly across both individual items and their compositions.

## Features
- XML parsing to build hierarchical item structures
- Price calculation that automatically aggregates through the composition hierarchy
- Item retrieval by name regardless of position in the hierarchy
- Ability to modify prices of individual items
- Item removal that maintains structural integrity

## Architecture - Composite Pattern Implementation
The system leverages the Composite Design Pattern with these key components:

- **Item Interface**: The component interface that defines operations for both leaf and composite objects
- **Book & CD Classes**: Leaf implementations that represent individual items
- **ItemList Class**: Composite implementation that can contain other items (both leaf and composite)
- **ItemManager**: Coordinates operations and XML parsing

## XML Parsing
The system reads an XML structure like:
```xml
<list name="root">
  <book name="B1" price="30" isbn="123"/>
  <list name="L1">
    <book name="B2" price="20" isbn="234"/>
    <cd name="C1" price="15"/>
  </list>
</list>
```

XML parsing is handled in a single location (ItemManager) to maintain separation of concerns, preventing XML-specific logic from spreading to model classes.

## Technical Details
- Pure Java implementation with no external dependencies
- DOM-based XML parsing using built-in JDK libraries
- Stream API used for efficient price aggregation
- Optional used for null-safe value returns

## Design Considerations
1. **Type Safety**: Uses the `isComposite()` method rather than `instanceof` for type checking
2. **Single Responsibility**: XML parsing is isolated from the model classes
3. **Open/Closed Principle**: New item types can be added without modifying existing code
4. **Recursion**: Utilized for traversing and operating on the item hierarchy

