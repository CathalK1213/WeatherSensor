package com.springboot.weathersensorapi.exception;

public class WeatherException extends RuntimeException {

    public WeatherException(String message) {
        super(message);
    }
}
