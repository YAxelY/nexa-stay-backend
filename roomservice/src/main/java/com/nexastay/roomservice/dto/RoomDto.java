package com.nexastay.roomservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexastay.roomservice.model.enums.RoomStatus;
import com.nexastay.roomservice.model.enums.RoomType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private UUID id;
    private String name;
    @JsonProperty("description")
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;
    private RoomType type;
    private RoomStatus status;
}
