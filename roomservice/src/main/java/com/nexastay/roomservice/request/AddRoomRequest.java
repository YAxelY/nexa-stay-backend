package com.nexastay.roomservice.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class AddRoomRequest {
    private UUID id;

    @NotNull(message = "Room name is required")
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;
    private String type;
    private String status;
}
