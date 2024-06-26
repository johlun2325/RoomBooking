package com.example.roombooking.dto;

import com.example.roombooking.models.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomLiteDTO {

    private Long id;
    private BigDecimal price;
    private RoomType roomType;

    public RoomLiteDTO(Long id, double price) {
        this.id = id;
        this.price = BigDecimal.valueOf(price);
    }
}
