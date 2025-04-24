package com.xpert.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Registers a ModelMapper bean for mapping between DTOs and entities.
     * This helps reduce boilerplate conversion code.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

  
}
