package com.nexastay.roomservice.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoomRequest {
    private UUID id;

    @NotNull(message = "Room name is required")
    private String name;

    @JsonProperty("description")
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;
    private String type;
    private String status;
}
