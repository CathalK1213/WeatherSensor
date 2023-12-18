package com.springboot.weathersensorapi.service;


import com.springboot.weathersensorapi.exception.WeatherException;
import com.springboot.weathersensorapi.model.WeatherModel;
import com.springboot.weathersensorapi.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class responsible for handling weather-related operations.
 * This includes adding weather metrics to the database and retrieving/querying metrics.
 */
@Service
public class WeatherService {

    /**
     * Repository for accessing weather metrics stored in the database.
     */
    private final WeatherRepository weatherRepository;

    /**
     * Constructs a new WeatherService with the specified WeatherRepository.
     *
     * @param weatherRepository Repository for accessing weather metrics.
     */
    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Adds a new weather metric to the database.
     *
     * @param weather The weather metric to be added.
     */
    public void addMetric(WeatherModel weather) {
        weatherRepository.save(weather);
    }

    /**
     * Retrieves weather metrics based on specified parameters.
     *
     * @param sensorId The ID of the sensor.
     * @param metric   The metric type (e.g., temperature).
     * @param start    The start of the date range (optional).
     * @param end      The end of the date range (optional).
     * @return A list of weather metrics based on the specified parameters.
     */
    public List<WeatherModel> getMetrics(String sensorId, String metric, LocalDateTime start, LocalDateTime end) {
        if (start == null && end == null) {
            // If no date range specified, fetch the latest data
            List<WeatherModel> metrics = weatherRepository.findBySensorIdAndMetric(sensorId, metric);

            if (metrics.isEmpty()) {
                throw new WeatherException("No weather metrics found for the specified criteria.");
            }

            return metrics;

        } else {
            // Fetch data within the specified date range
            List<WeatherModel> metrics = weatherRepository.findBySensorIdAndMetricAndTimestampBetween(sensorId, metric, start, end);

            if (metrics.isEmpty()) {
                throw new WeatherException("No weather metrics found for the specified criteria.");
            }

            return metrics;
        }
    }

    /**
     * Calculates a statistical value (min, max, sum, average) for a list of weather metrics.
     *
     * @param metrics   The list of weather metrics.
     * @param statistic The type of statistic to calculate.
     * @return The calculated statistical value.
     * @throws IllegalArgumentException if an invalid statistic type is provided.
     */
    public double calculateStatistic(List<WeatherModel> metrics, String statistic) {
        if (metrics.isEmpty()) {
            throw new IllegalArgumentException("Metrics list is empty.");
        }
        switch (statistic.toLowerCase()) {
            case "min":
                return metrics.stream().mapToDouble(WeatherModel::getValue).min().orElse(0.0);
            case "max":
                return metrics.stream().mapToDouble(WeatherModel::getValue).max().orElse(0.0);
            case "sum":
                return metrics.stream().mapToDouble(WeatherModel::getValue).sum();
            case "average":
                return metrics.stream().mapToDouble(WeatherModel::getValue).average().orElse(0.0);
            default:
                // Handle invalid statistic
                throw new IllegalArgumentException("Invalid statistic");
        }
    }

    /**
     * Retrieves the calculated statistical value based on specified parameters.
     *
     * @param sensorId  The ID of the sensor.
     * @param metric    The metric type (e.g., temperature).
     * @param statistic The type of statistic to calculate (min, max, sum, average).
     * @param start     The start of the date range (optional).
     * @param end       The end of the date range (optional).
     * @return The calculated statistical value.
     */
    public double getStatistic(String sensorId, String metric, String statistic, LocalDateTime start, LocalDateTime end) {
        List<WeatherModel> metrics = getMetrics(sensorId, metric, start, end);
        return calculateStatistic(metrics, statistic);
    }
}



