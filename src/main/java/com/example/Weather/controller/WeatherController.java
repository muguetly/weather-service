package com.example.Weather.controller;

import com.example.Weather.dto.ForecastDayDto;
import com.example.Weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "날씨 API", description = "날씨 정보 조회 API")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "도시명으로 현재 날씨 조회", description = "도시명을 입력하여 현재 날씨를 조회합니다.")
    @GetMapping("/api/weather")
    public String getWeather(
            @Parameter(description = "도시명 (예: Seoul, Tokyo, Busan)", required = true)
            @RequestParam String city) {
        return weatherService.getWeatherByCity(city);
    }

    @Operation(summary = "좌표로 현재 날씨 조회", description = "위도와 경도를 입력하여 현재 날씨를 조회합니다.")
    @GetMapping("/api/weather/location")
    public String getWeatherByLocation(
            @Parameter(description = "위도 (예: 37.5665)", required = true)
            @RequestParam double lat,
            @Parameter(description = "경도 (예: 126.9780)", required = true)
            @RequestParam double lon) {
        return weatherService.getWeatherByLocation(lat, lon);
    }

    @Operation(summary = "도시명으로 3일 예보 조회", description = "도시명을 입력하여 3일간의 날씨 예보를 조회합니다.")
    @GetMapping("/api/weather/forecast")
    public List<ForecastDayDto> getForecast(
            @Parameter(description = "도시명 (예: Seoul, Tokyo, Busan)", required = true)
            @RequestParam String city) {
        return weatherService.get3DayForecast(city);
    }

    @Operation(summary = "좌표로 3일 예보 조회", description = "위도와 경도를 입력하여 3일간의 날씨 예보를 조회합니다.")
    @GetMapping("/api/weather/forecast/location")
    public List<ForecastDayDto> getForecastByLocation(
            @Parameter(description = "위도 (예: 37.5665)", required = true)
            @RequestParam double lat,
            @Parameter(description = "경도 (예: 126.9780)", required = true)
            @RequestParam double lon) {
        return weatherService.get3DayForecastByLocation(lat, lon);
    }
}