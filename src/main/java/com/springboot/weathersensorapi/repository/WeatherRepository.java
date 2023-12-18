package com.springboot.weathersensorapi.repository;

import com.springboot.weathersensorapi.model.WeatherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA repository interface for accessing weather metrics stored in the database.
 */
public interface WeatherRepository extends JpaRepository<WeatherModel, Long> {

    /**
     * Retrieves weather metrics for a specific sensor and metric.
     *
     * @param sensorId The ID of the sensor.
     * @param metric   The metric type (e.g., temperature).
     * @return A list of weather metrics.
     */
    List<WeatherModel> findBySensorIdAndMetric(String sensorId, String metric);

    /**
     * Retrieves weather metrics for a specific sensor, metric, and within a given date range.
     *
     * @param sensorId The ID of the sensor.
     * @param metric   The metric type (e.g., temperature).
     * @param start    The start of the date range.
     * @param end      The end of the date range.
     * @return A list of weather metrics within the specified date range.
     */
    List<WeatherModel> findBySensorIdAndMetricAndTimestampBetween(String sensorId, String metric, LocalDateTime start, LocalDateTime end);
}
