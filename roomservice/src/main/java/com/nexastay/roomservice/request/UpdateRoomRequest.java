package com.nexastay.roomservice.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateRoomRequest {
    private UUID id;

    private String number;
    @JsonProperty("description")
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;
    private String type;
    private String status;
}
