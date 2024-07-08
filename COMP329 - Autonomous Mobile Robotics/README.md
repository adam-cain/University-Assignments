# COMP329: Robotics - Assignment 1

## Overview

This repository contains the source code for Assignment 1 of the COMP329: Robotics course for the academic year 2023/2024. The aim of this assignment is to implement an obstacle avoidance algorithm to enable a robot to navigate from a start location to an end location within a specified environment.

## Objective

The primary goal of this assignment is to develop a robot controller that can determine the appropriate direction to move towards its destination while avoiding obstacles. The robot should track the distance traveled and provide telemetry data to understand its navigation decisions.

## Detailed Task Description

### Environment Setup

1. **Environment**: Download the environment file for the assignment. The robot will navigate within a 12x9 environment starting at position (-4.5, 3, 0) and ending at the location of the traffic cone at approximately (2.75, -3.26).
2. **Webots**: Use Webots R2022b or a compatible version. Ensure the robot tracks its location using the supervisor mode as detailed in Lab 3.

### Implementation Guidelines

1. **Programming Language**: Implement the solution in Java or Python using standard libraries (including numpy). Utilize the Webots API and code from COMP329 labs as needed.
2. **Obstacle Avoidance Algorithm**: Implement an obstacle avoidance algorithm, preferably Bug2 or Bug1 (with higher marks for Bug2). Extra marks can be awarded for comparing both approaches.
3. **Distance Tracking**: Track the distance traveled by the robot and report this in the final report.
4. **Telemetry Reporting**: Report the robot's progress and telemetry data (location, wheel speed, current status) as it navigates around obstacles. Present this data textually or graphically.
5. **Code Quality**: Focus on the style and quality of your code, following best practices and ensuring readability.

### Features to Implement

1. **Obstacle Avoidance**: Implement a robust obstacle avoidance algorithm without making assumptions about the obstacle configuration.
2. **Destination Tracking**: Ensure the robot navigates towards the destination and terminates upon reaching the traffic cone location.
3. **Telemetry Display**: Enhance the presentation of telemetry data, making it easy to follow the robot's decisions.

### Reporting

1. **Distance Report**: Include the total distance traveled by the robot in the final report.
2. **Algorithm Discussion**: Write a short (500-1000 word) discussion on the implemented obstacle avoidance strategy, challenges faced, and reflections on the approach taken.