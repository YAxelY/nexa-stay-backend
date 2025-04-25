package com.nexastay.roomservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexastay.roomservice.model.enums.RoomStatus;
import com.nexastay.roomservice.model.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="rooms")
public class Room {
    
    @Id
    @GeneratedValue
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String number;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @JsonProperty
    private RoomType type;

    @Enumerated(EnumType.STRING)
    @JsonProperty
    private RoomStatus status = RoomStatus.AVAILABLE;

//    @ManyToOne
//    @JoinColumn(name = "hotel_id", nullable = false)
//    private Hotel hotel;

    // Constructors, getters, and setters

    public Room(String number, Integer capacity, String description, String imageUrl, BigDecimal price, RoomStatus status, RoomType type){
        this.number = number;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.capacity = capacity;
        this.type = type;
        this.status = status;
    }

}
