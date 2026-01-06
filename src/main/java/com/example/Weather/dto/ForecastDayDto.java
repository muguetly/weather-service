package com.example.Weather.dto;

public class ForecastDayDto {

    private String date;
    private double min;
    private double max;
    private String weather;

    public ForecastDayDto(String date, double min, double max, String weather) {
        this.date = date;
        this.min = min;
        this.max = max;
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public String getWeather() {
        return weather;
    }
}
