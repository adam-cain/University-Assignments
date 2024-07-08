# COMP319: Software Engineering II - Assignment 1

## Overview

This repository contains the source code for Assignment 1 of the COMP319: Software Engineering II course for the academic year 2023/2024. The objective of this assignment is to design the class structure for a game based on the classic Space Invaders, focusing on the use of object-oriented design patterns.

## Objective

The primary goal of this assignment is to demonstrate proficiency in object-oriented design patterns by creating a well-structured class design for a Space Invaders game. The game design should include the following key components:
- **Factory Pattern**
- **Chain of Responsibility**
- **Singleton**
- **Open/Closed Principle**
- **Single Responsibility Principle**

## Instructions

1. **Class Design**: Create a class design for a Space Invaders game. Each component of the game must be modeled as a class. The design should focus on the structure and relationships between classes without requiring a playable game. Stubs should be included for:
    - Drawing objects on the screen
    - Receiving player input
    - Playing sounds

2. **Patterns and Principles**: Use the following patterns and principles:
    - **Factory Pattern**: For creating instances of different game objects.
    - **Chain of Responsibility**: To handle various game actions and events.
    - **Singleton Pattern**: To ensure a single instance of certain classes.
    - **Open/Closed Principle**: To allow the game to be extendable without modifying existing code.
    - **Single Responsibility Principle**: To ensure each class has one responsibility.

3. **Java Code**: All code must be written in Java™. Follow the coding style guidelines provided [here](https://cgi.csc.liv.ac.uk/~coopes/comp201/codeStyleGuidelinesCS.pdf). Use Java documentation comments for each public method and class.

4. **Deliverables**: Submit a zip file containing all the source code for the game, including all Java source classes, interfaces, and other files. Ensure the code compiles without errors. Provide a `Main.java` class as the entry point for the game.