# Weather Sensor API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/17-relnote-issues.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green.svg)](https://spring.io/projects/spring-boot)

## Overview

The Weather Sensor API is a Spring Boot application that provides a RESTful API for handling weather-related operations.
It allows you to add weather metrics to a database and query metrics based on various parameters.

## Features

- Add new weather metrics
- Query weather metrics based on sensor ID, metric type, and date range
- Calculate statistical values (min, max, sum, average) for weather metrics

## Table of Contents

- [Weather Sensor API](#weather-sensor-api)
    - [Overview](#overview)
    - [Features](#features)
    - [Table of Contents](#table-of-contents)
    - [Getting Started](#getting-started)
        - [Prerequisites](#prerequisites)
        - [Running Locally](#running-locally)
        - [Running with Docker](#running-with-docker)
    - [Functionality](#functionality)
        - [Add Weather Metric](#add-weather-metric)
        - [Query Weather Metrics](#query-weather-metrics)
    - [Testing with Postman](#testing-with-postman)
    - [Configuration](#configuration)

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker 

### Running Locally

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/weather-sensor-api.git

2. **Navigate to the project directory:**

   ```bash
   cd weather-sensor-api

3. **Build the project:**

   ```bash
   mvn clean package

4. **Run the Application**
   ```bash
   java -jar target/weather-api.jar

### Running with Docker

1. **Navigate to the project directory:**

   ```bash
   cd weather-sensor-api

2. **Spin Up PostgreSQL Container**

   ```bash
   docker run --name my-postgres-container -e POSTGRES_USER=my_user -e POSTGRES_PASSWORD=my_password -e POSTGRES_DB=my_weather_db -d -p 5432:5432 postgres:alpine

3. **Build Docker Image:**

   ```bash
   docker build -t weather-api .

4. **Run the Docker Container**

   ```bash
    docker run -p 8080:8080 -e DB_HOST=my-postgres-host -e DB_PORT=5432 -e DB_NAME=my_weather_db -e DB_USERNAME=my_user                   
   -e DB_PASSWORD=my_password weather-api
   ```

In this version, the Docker run command is presented in a single line for clarity, and a note is added to inform users
about the need to replace the placeholders with their specific configuration values.

### Functionality

#### Add Weather Metric

- **Endpoint:** `POST /api/weather/add`
- **Description:** Adds a new weather metric to the database.
- **Request Example:**

    ```bash
    curl --location 'http://localhost:8080/api/weather/add' \
    --header 'Content-Type: application/json' \
    --data '    {
    "sensorId": "sensor2",
    "metric": "humidity",
    "value": 60.0,
    "timestamp": "2023-01-01T12:10:00"
    }'
    ```

#### Query Weather Metrics

- **Endpoint:** `GET /api/weather/query`
- **Description:** Retrieves and calculates statistical values for weather metrics based on specified parameters.
- **Request Examples:**

    ```bash
    # Example 1: Query 
    curl --location 'http://localhost:8080/api/weather/query?sensorId=sensor2&metric=humidity&statistic=sum&start=2023-01-01T00%3A00%3A00&end=2023-01-31T00%3A00%3A00'
    ```

## Testing with Postman

1. Download and install [Postman](https://www.postman.com/).

2. Import the provided Postman
   collection: [WeatherSensorAPI.postman_collection.json](postman/WeatherSensorAPI.postman_collection.json).

    - Open Postman.
    - Click on the "Import" button in the top left corner.
    - Choose the "Link" tab.
    - Paste the URL of the Postman collection
      file: [WeatherSensorAPI.postman_collection.json](postman/WeatherSensorAPI.postman_collection.json).
    - Click "Import" to add the collection to your Postman workspace.

3. Open the imported collection in Postman.

4. Execute the included requests to test various API endpoints.

## Configuration

- Configure the PostgreSQL database connection in `src/main/resources/application.properties`.
- Adjust application properties such as server port, database connection, etc., based on your requirements.
