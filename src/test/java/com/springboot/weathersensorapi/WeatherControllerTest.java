package com.springboot.weathersensorapi;

import com.springboot.weathersensorapi.model.WeatherModel;
import com.springboot.weathersensorapi.repository.WeatherRepository;
import com.springboot.weathersensorapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class WeatherControllerTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void testAddMetric() {
        WeatherModel weather = new WeatherModel("sensor1", "temperature", 25.0, LocalDateTime.now());

        // Mock the repository's save method
        Mockito.when(weatherRepository.save(any(WeatherModel.class))).thenReturn(weather);

        // Call the service method
        weatherService.addMetric(weather);

        // Verify that the save method was called
        Mockito.verify(weatherRepository, Mockito.times(1)).save(any(WeatherModel.class));
    }

    @Test
    public void testGetMetricsWithNoDateRange() {
        String sensorId = "sensor1";
        String metric = "temperature";

        List<WeatherModel> mockMetrics = new ArrayList<>();
        mockMetrics.add(new WeatherModel(sensorId, metric, 25.0, LocalDateTime.now()));

        // Mock the repository's findBySensorIdAndMetric method
        Mockito.when(weatherRepository.findBySensorIdAndMetric(sensorId, metric)).thenReturn(mockMetrics);

        // Call the service method with no date range
        List<WeatherModel> result = weatherService.getMetrics(sensorId, metric, null, null);

        // Verify that the repository method was called and the result is as expected
        Mockito.verify(weatherRepository, Mockito.times(1)).findBySensorIdAndMetric(sensorId, metric);
        assertEquals(mockMetrics, result);
    }

    @Test
    public void testGetMetricsWithDateRange() {
        String sensorId = "sensor1";
        String metric = "temperature";
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now();

        List<WeatherModel> mockMetrics = new ArrayList<>();
        mockMetrics.add(new WeatherModel(sensorId, metric, 25.0, LocalDateTime.now()));

        // Mock the repository's findBySensorIdAndMetricAndTimestampBetween method
        Mockito.when(weatherRepository.findBySensorIdAndMetricAndTimestampBetween(sensorId, metric, start, end))
                .thenReturn(mockMetrics);

        // Call the service method with a date range
        List<WeatherModel> result = weatherService.getMetrics(sensorId, metric, start, end);

        // Verify that the repository method was called and the result is as expected
        Mockito.verify(weatherRepository, Mockito.times(1))
                .findBySensorIdAndMetricAndTimestampBetween(sensorId, metric, start, end);
        assertEquals(mockMetrics, result);
    }

    @Test
    public void testCalculateStatistic() {
        List<WeatherModel> metrics = new ArrayList<>();
        metrics.add(new WeatherModel("sensor1", "temperature", 25.0, LocalDateTime.now()));
        metrics.add(new WeatherModel("sensor1", "temperature", 30.0, LocalDateTime.now()));

        // Test minimum value
        assertEquals(25.0, weatherService.calculateStatistic(metrics, "min"));

        // Test maximum value
        assertEquals(30.0, weatherService.calculateStatistic(metrics, "max"));

        // Test sum
        assertEquals(55.0, weatherService.calculateStatistic(metrics, "sum"));

        // Test average
        assertEquals(27.5, weatherService.calculateStatistic(metrics, "average"));

        // Test invalid statistic
        assertThrows(IllegalArgumentException.class, () -> weatherService.calculateStatistic(metrics, "invalid"));
    }

    @Test
    public void testGetStatistic() {
        String sensorId = "sensor1";
        String metric = "temperature";
        String statistic = "average";
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now();

        List<WeatherModel> mockMetrics = new ArrayList<>();
        mockMetrics.add(new WeatherModel(sensorId, metric, 25.0, LocalDateTime.now()));
        mockMetrics.add(new WeatherModel(sensorId, metric, 30.0, LocalDateTime.now()));

        // Mock the getMetrics method
        Mockito.when(weatherService.getMetrics(sensorId, metric, start, end)).thenReturn(mockMetrics);

        // Call the getStatistic method
        double result = weatherService.getStatistic(sensorId, metric, statistic, start, end);

        // Verify that the getMetrics method was called and the result is as expected
        Mockito.verify(weatherService, Mockito.times(1)).getMetrics(sensorId, metric, start, end);
        assertEquals(27.5, result);
    }

    @Test
    public void testGetStatisticWithNoMetrics() {
        String sensorId = "sensor1";
        String metric = "temperature";
        String statistic = "average";
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now();

        // Mock the getMetrics method to return an empty list
        Mockito.when(weatherService.getMetrics(sensorId, metric, start, end)).thenReturn(new ArrayList<>());

        // Call the getStatistic method
        double result = weatherService.getStatistic(sensorId, metric, statistic, start, end);

        // Verify that the getMetrics method was called and the result is the default value
        Mockito.verify(weatherService, Mockito.times(1)).getMetrics(sensorId, metric, start, end);
        assertEquals(Double.NaN, result);
    }

}
