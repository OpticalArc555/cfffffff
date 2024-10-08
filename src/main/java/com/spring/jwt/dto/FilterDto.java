package com.spring.jwt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FilterDto {
    private Integer minPrice;
    private Integer maxPrice;
    private String area;
    private String brand;
    private String model;
    private String transmission;
    private String fuelType;
    private int year;
    private String carType;

    public FilterDto(Integer minPrice, Integer maxPrice, String area, String brand, String model, String transmission, String fuelType, Integer year,String carType) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.area = area;
        this.brand = brand;
        this.model = model;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.year = year != null ? year : 0;
        this.carType=carType;
    }
}
