package com.nexastay.roomservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
