package com.company.disaster_response_app.disaster_reporting_service.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {



    // ModelMapper configuration for the entity to DTOs conversion
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }
}
