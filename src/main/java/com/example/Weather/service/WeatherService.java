package com.example.Weather.service;

import com.example.Weather.dto.ForecastDayDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 현재 날씨 (기존)
    public String getWeatherByCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather"
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric"
                + "&lang=kr";

        return restTemplate.getForObject(url, String.class);
    }

    // ⭐ 3일 예보 (완성형)
    public List<ForecastDayDto> get3DayForecast(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast"
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric"
                + "&lang=kr";

        String json = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode list = root.get("list");

            Map<LocalDate, List<JsonNode>> grouped = new LinkedHashMap<>();

            for (JsonNode item : list) {
                String dtTxt = item.get("dt_txt").asText(); // yyyy-MM-dd HH:mm:ss
                LocalDate date = LocalDate.parse(dtTxt.substring(0, 10));

                grouped.computeIfAbsent(date, d -> new ArrayList<>()).add(item);
            }

            List<ForecastDayDto> result = new ArrayList<>();

            for (Map.Entry<LocalDate, List<JsonNode>> entry : grouped.entrySet()) {
                if (result.size() == 3) break;

                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;
                Map<String, Integer> weatherCount = new HashMap<>();

                for (JsonNode node : entry.getValue()) {
                    double temp = node.get("main").get("temp").asDouble();
                    min = Math.min(min, temp);
                    max = Math.max(max, temp);

                    String weather = node.get("weather").get(0).get("description").asText();
                    weatherCount.put(weather, weatherCount.getOrDefault(weather, 0) + 1);
                }

                String 대표날씨 = Collections.max(
                        weatherCount.entrySet(),
                        Map.Entry.comparingByValue()
                ).getKey();

                result.add(new ForecastDayDto(
                        entry.getKey().toString(),
                        min,
                        max,
                        대표날씨
                ));
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Forecast parsing error", e);
        }
    }
}
