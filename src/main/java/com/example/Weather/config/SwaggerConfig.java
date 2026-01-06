package com.example.Weather.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI weatherAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("날씨 API")
                        .description("도시명 및 좌표 기반 날씨 조회 API")
                        .version("1.0.0"));
    }
}