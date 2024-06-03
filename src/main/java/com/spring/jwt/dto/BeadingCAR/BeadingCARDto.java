package com.spring.jwt.dto.BeadingCAR;

import com.spring.jwt.entity.BeadingCAR;
import com.spring.jwt.entity.Status;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
@Data
public class BeadingCARDto {

    private Integer beadingCarId;


    private Boolean acFeature;


    private Boolean musicFeature;


    private String area;


    private String bodyType;


    private String brand;


    private Boolean carInsurance;


    private String carStatus;


    private String city;


    private String color;


    private String description;


    private String fuelType;


    private int kmDriven;


    private String model;


    private int noOfWheels;


    private int ownerSerial;


    private Boolean powerWindowFeature;


    private int price;


    private Boolean rearParkingCameraFeature;


    private String registration;


    private String safetyDescription;


    private String transmission;


    private String tyre;


    private int year;


    private LocalDate date;


    private int userId;


    public BeadingCARDto() {
    }

    public BeadingCARDto(BeadingCAR beadingCAR) {
        this.beadingCarId = beadingCAR.getBeadingCarId();
        this.acFeature = beadingCAR.getAcFeature();
        this.musicFeature = beadingCAR.getMusicFeature();
        this.area = beadingCAR.getArea();
        this.bodyType = beadingCAR.getBodyType();
        this.brand = beadingCAR.getBrand();
        this.carInsurance = beadingCAR.getCarInsurance();
        this.carStatus = beadingCAR.getCarStatus();
        this.city = beadingCAR.getArea();
        this.color = beadingCAR.getColor();
        this.description = beadingCAR.getDescription();
        this.fuelType = beadingCAR.getFuelType();
        this.kmDriven = beadingCAR.getKmDriven();
        this.model = beadingCAR.getModel();
        this.noOfWheels = beadingCAR.getNoOfWheels();
        this.ownerSerial = beadingCAR.getOwnerSerial();
        this.powerWindowFeature = beadingCAR.getPowerWindowFeature();
        this.price = beadingCAR.getPrice();
        this.rearParkingCameraFeature = beadingCAR.getRearParkingCameraFeature();
        this.registration = beadingCAR.getRegistration();
        this.safetyDescription = beadingCAR.getSafetyDescription();
        this.transmission = beadingCAR.getTransmission();
        this.tyre = beadingCAR.getTyre();
        this.year = beadingCAR.getYear();
        this.date = beadingCAR.getDate();
        this.userId = beadingCAR.getUserId();
    }
}
