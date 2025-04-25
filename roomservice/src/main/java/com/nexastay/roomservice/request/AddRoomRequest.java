package com.nexastay.roomservice.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class AddRoomRequest {
    private UUID id;

    private String number;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;
    private String type;
    private String status;
}
