# COMP 315: Cloud Computing for E-Commerce - Assignment 2

## Overview

This repository contains the source code for Assignment 2 of the COMP 315: Cloud Computing for E-Commerce course for the academic year 2023/2024. The assignment focuses on enhancing a basic e-commerce website using TypeScript and React, showcasing your ability to create dynamic and responsive web applications.

## Objective

The primary goal of this assignment is to demonstrate proficiency in using TypeScript and React to build a dynamic and reactive e-commerce front-end website. By completing this assignment, you will gain experience in developing interactive web applications that adapt to different screen sizes and provide real-time updates without needing full page reloads.

## Problem Description

You are provided with a skeleton e-commerce website and the assets needed to populate it. Currently, the site displays the name, picture, rating, and price of a collection of items for sale and allows for basic search functionality. Your task is to add the following features:

1. **Search Results Indicator**: Display the number of search results or available products.
2. **Sorting Functionality**: Enable sorting of items by name, price, or rating.
3. **In-Stock Filter**: Allow users to filter search results to show only in-stock items.
4. **Shopping Basket**: Add functionality to add or remove items from the shopping basket and display the total cost.

## Detailed Instructions

### Initial Setup

1. Ensure you have Node.js and Vite installed on your computer.
2. Download and extract the provided zip file containing the skeleton code.
3. Navigate to the extracted folder in your terminal.
4. Run `npm install` to install the necessary dependencies.
5. Start the local server with `npm run dev` and open the website in your browser using the provided localhost address.

### Developing the Website

#### 1. Search Results Indicator

- Add a notification in the `results-indicator` paragraph tag to show the number of products matching the current search query.
- Display the total number of products when the search bar is empty.
- Show a specific message when no search results are found.

#### 2. Enhance Search Functionality

- Implement sorting functionality for the select tag within the search bar to sort products by name, price, or rating.
- Add a checkbox input to filter the results to show only in-stock items.

#### 3. Adding to the Shopping Basket

- Modify the "Add to basket" button to display "Out of stock" and disable it when the product quantity is zero.
- Implement a function to add products to the shopping basket, increasing the quantity for duplicate items.

#### 4. Visualising the Basket

- Display a message if the basket is empty.
- Show the contents of the basket with each item’s details and a "Remove" button.
- Update the basket when items are removed, and show the total cost at the bottom.