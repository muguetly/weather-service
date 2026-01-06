package com.example.Weather.controller;

import com.example.Weather.dto.ForecastDayDto;
import com.example.Weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // ✅ 기존: 도시 현재 날씨
    @GetMapping("/api/weather")
    public String getWeather(@RequestParam String city) {
        return weatherService.getWeatherByCity(city);
    }

    // ✅ 신규: 도시 3일 예보
    @GetMapping("/api/weather/forecast")
    public List<ForecastDayDto> getForecast(@RequestParam String city) {
        return weatherService.get3DayForecast(city);
    }
}
