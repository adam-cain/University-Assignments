# COMP 315: Cloud Computing for E-Commerce - Assignment 1

## Overview

This repository contains the source code for Assignment 1 of the COMP 315: Cloud Computing for E-Commerce course for the academic year 2023/2024. The assignment focuses on implementing a series of data cleaning operations using JavaScript, showcasing proficiency in data manipulation and cleaning techniques.

## Objective

The primary goal of this assignment is to demonstrate the ability to use JavaScript for data cleaning and manipulation tasks. You will be working on a dataset of user information and performing various data cleaning operations to ensure the data is accurate and well-formatted.

## Problem Description

You are provided with a raw dataset of user information. Your task is to perform the following operations:

1. **Setup a JavaScript Class**: Create a JavaScript class called `DataProcessing` with functions to load, format, and clean the data.
2. **Convert Data Format**: Process the raw data into a structured format as specified.
3. **Fix Erroneous Values**: Identify and correct erroneous data entries.
4. **Implement Queries**: Write functions to perform specific queries on the cleaned data.

## Detailed Instructions

### Initial Setup

1. Create a JavaScript file called `DataProcessing.js`.
2. Define a class named `DataProcessing`.
3. Implement a function `loadCSV(filename)` to load the data from a CSV file and store it in a global variable `rawUserData`.
4. Implement a function `formatData()` to process the raw data and store the formatted data in a global variable `formattedUserData`.
5. Implement a function `cleanData()` to clean the formatted data and store the cleaned data in a global variable `cleanedUserData`.

### Data Formatting

- Process the raw data from CSV format (columns: title and full name, date of birth, age, email) to a structured JSON format.
- Ensure the data adheres to specified rules, such as correct capitalization, valid date formats, and unique email addresses.

### Data Cleaning

- Correct any data inconsistencies, such as converting ages to integers and fixing date formats.
- Fill in missing values where possible using the available data.
- Store the cleaned data in `cleanedUserData`.

### Queries

Implement the following functions to perform queries on the cleaned data:

1. `mostCommonSurname()`: Find the most common surname.
2. `averageAge()`: Calculate the average age of users.
3. `youngestDr()`: Find the youngest individual with the title "Dr".
4. `mostCommonMonth()`: Identify the most common birth month.
5. `percentageTitles()`: Calculate the percentage of each title in the dataset.
6. `percentageAltered()`: Determine the percentage of altered values between `formattedUserData` and `cleanedUserData`.