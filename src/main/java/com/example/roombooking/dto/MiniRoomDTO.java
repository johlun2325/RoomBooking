package com.example.roombooking.dto;

import com.example.roombooking.models.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniRoomDTO {

    private Long id;
    private double price;
    private RoomType roomType;

}
