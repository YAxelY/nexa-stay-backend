package com.nexastay.roomservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {
    private String number;
    @JsonProperty("description")
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;
    private String type;
    private String status;
}
