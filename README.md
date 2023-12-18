# Weather Sensor API

## Overview

The Weather Sensor API is a Spring Boot application that manages weather metrics captured by various sensors. The API provides endpoints for adding new weather metrics and querying existing metrics based on specified parameters.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or later
- Maven (for building and managing dependencies)
- PostgreSQL database (configured in `application.properties`)
- [Postman](https://www.postman.com/) (for testing API calls)

### Build and Run

1. Clone the repository:

    ```bash
    git clone https://github.com/CathalK1213/weather-sensor-api.git
    cd weather-sensor-api
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    mvn spring-boot:run
    ```

   The application will be accessible at [http://localhost:8080](http://localhost:8080).

### Add Weather Metric

- **Endpoint:** `POST /api/weather/add`
- **Description:** Adds a new weather metric to the database.
- **Request Example:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"sensorId": "sensor1", "metric": "temperature", "value": 25.5, "timestamp": "2023-01-01T12:00:00"}' http://localhost:8080/api/weather/add
    ```

### Query Weather Metrics

- **Endpoint:** `GET /api/weather/query`
- **Description:** Retrieves and calculates statistical values for weather metrics based on specified parameters.
- **Request Examples:**

    ```bash
    # Example 1: Query Average Temperature for a Sensor
    curl -X GET "http://localhost:8080/api/weather/query?sensorId=sensor1&metric=temperature&statistic=average&start=2023-01-01T00:00:00&end=2023-01-08T00:00:00"
    ```

    ```bash
    # Example 2: Query Min Humidity for a Sensor
    curl -X GET "http://localhost:8080/api/weather/query?sensorId=sensor2&metric=humidity&statistic=min&start=2023-01-01T00:00:00&end=2023-01-08T00:00:00"
    ```

## Testing with Postman

1. Download and install [Postman](https://www.postman.com/).

2. Import the provided Postman collection: [WeatherSensorAPI.postman_collection.json](postman/WeatherSensorAPI.postman_collection.json).

3. Open the imported collection in Postman.

4. Execute the included requests to test various API endpoints.

## Configuration

- Configure the PostgreSQL database connection in `src/main/resources/application.properties`.
- Adjust application properties such as server port, database connection, etc., based on your requirements.
