package com.spring.jwt.Bidding.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllSalesPersonDto {

    private String message;
    private Integer totalPages;
    private List<SalesPersonDto> list;
    private String exception;

    public AllSalesPersonDto(String message) {
        this.message = message;
    }
}
