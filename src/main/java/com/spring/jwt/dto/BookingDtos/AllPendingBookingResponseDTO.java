package com.spring.jwt.dto.BookingDtos;

import com.spring.jwt.dto.PendingBookingDTO;
import lombok.Data;

import java.util.List;

@Data

public class AllPendingBookingResponseDTO {
    private String message;
    private List<PendingBookingDTO> list;
    private String exception;

    public AllPendingBookingResponseDTO(String message){
        this.message=message;
    }

}
