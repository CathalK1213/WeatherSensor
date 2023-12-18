package com.springboot.weathersensorapi.controller;

import com.springboot.weathersensorapi.model.WeatherModel;
import com.springboot.weathersensorapi.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RESTful controller for handling weather-related API requests.
 * This includes adding weather metrics and querying metrics.
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    /**
     * Service for handling weather-related operations.
     */
    private final WeatherService weatherService;

    /**
     * Constructs a new WeatherController with the specified WeatherService.
     *
     * @param weatherService Service for handling weather-related operations.
     */
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Adds a new weather metric to the database.
     *
     * @param weather The weather metric to be added.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addMetric(@RequestBody WeatherModel weather) {
        weatherService.addMetric(weather);
        return ResponseEntity.ok("Metric added successfully");
    }
    /**
     * Retrieves and calculates a statistical value for weather metrics based on the provided parameters.
     *
     * @param sensorId  Identifier of the sensor.
     * @param metric    Type of metric (e.g., temperature, humidity).
     * @param statistic Type of statistical calculation to perform.
     * @param start     Start timestamp of the range (optional).
     * @param end       End timestamp of the range (optional).
     * @return Calculated statistical value.
     */
    @GetMapping("/query")
    public double queryMetric(@RequestParam String sensorId,
                              @RequestParam String metric,
                              @RequestParam String statistic,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<WeatherModel> metrics = weatherService.getMetrics(sensorId, metric, start, end);
        return weatherService.calculateStatistic(metrics, statistic);
    }
}



