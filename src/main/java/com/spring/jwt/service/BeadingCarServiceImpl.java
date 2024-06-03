package com.spring.jwt.service;

import com.spring.jwt.Interfaces.BeadingCarService;
import com.spring.jwt.dto.BeadingCAR.BeadingCARDto;
import com.spring.jwt.entity.BeadingCAR;
import com.spring.jwt.exception.BeadingCarNotFoundException;
import com.spring.jwt.repository.BeadingCarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class BeadingCarServiceImpl implements BeadingCarService {

    private final BeadingCarRepo beadingCarRepo;

    @Override
    public String AddBCar(BeadingCARDto beadingCARDto) {
        BeadingCAR beadingCAR = new  BeadingCAR(beadingCARDto);
        beadingCarRepo.save(beadingCAR);
        return "beadingCAR added";
    }

    @Override
    public String editCarDetails(BeadingCARDto beadingCARDto, Integer beadingCarId) {
        BeadingCAR beadingCAR = beadingCarRepo.findById(beadingCarId).orElseThrow(()->new BeadingCarNotFoundException(("beadingCAR not found"), HttpStatus.NOT_FOUND));
        beadingCAR.setAcFeature(beadingCARDto.getAcFeature());
        beadingCAR.setMusicFeature(beadingCARDto.getMusicFeature());
        beadingCAR.setArea(beadingCARDto.getArea());
        beadingCAR.setDate(beadingCARDto.getDate());
        beadingCAR.setBodyType(beadingCARDto.getBodyType());
        beadingCAR.setBrand(beadingCARDto.getBrand());
        beadingCAR.setCarInsurance(beadingCARDto.getCarInsurance());
        beadingCAR.setCarStatus(beadingCARDto.getCarStatus());
        beadingCAR.setCity(beadingCARDto.getCity());
        beadingCAR.setColor(beadingCARDto.getColor());
        beadingCAR.setDescription(beadingCARDto.getDescription());
        beadingCAR.setFuelType(beadingCARDto.getFuelType());
        beadingCAR.setKmDriven(beadingCARDto.getKmDriven());
        beadingCAR.setModel(beadingCARDto.getModel());
        beadingCAR.setNoOfWheels(beadingCARDto.getNoOfWheels());
        beadingCAR.setPowerWindowFeature(beadingCARDto.getPowerWindowFeature());
        beadingCAR.setOwnerSerial(beadingCARDto.getOwnerSerial());
        beadingCAR.setPowerWindowFeature(beadingCARDto.getPowerWindowFeature());
        beadingCAR.setPrice(beadingCARDto.getPrice());
        beadingCAR.setRearParkingCameraFeature(beadingCARDto.getRearParkingCameraFeature());
        beadingCAR.setRegistration(beadingCARDto.getRegistration());
        beadingCAR.setSafetyDescription(beadingCARDto.getSafetyDescription());
        beadingCAR.setTransmission(beadingCARDto.getTransmission());
        beadingCAR.setTyre(beadingCARDto.getTyre());
        beadingCAR.setYear(beadingCARDto.getYear());
        beadingCarRepo.save(beadingCAR);
        return "beadingcar edited";

    }

    @Override
    public List<BeadingCARDto> getAllBeadingCars() {
        List<BeadingCAR> beadingCars = beadingCarRepo.findAll();
        List<BeadingCARDto> dtos = new ArrayList<>();
        for (BeadingCAR beadingCAR : beadingCars) {
            dtos.add(new BeadingCARDto(beadingCAR));
        }
        return dtos;
    }

    @Override
    public String deleteBCar(Integer beadingCarId) {
        beadingCarRepo.deleteById(beadingCarId);
        return "Beading car deleted successfully";
    }

    @Override
    public BeadingCARDto getBCarById(Integer beadingCarId) {
        BeadingCAR beadingCAR = beadingCarRepo.findById(beadingCarId)
                .orElseThrow(() -> new BeadingCarNotFoundException("Beading car not found with id: " + beadingCarId, HttpStatus.NOT_FOUND));
        return new BeadingCARDto(beadingCAR);
    }

    @Override
    public List<BeadingCARDto> getByUserId(int UserId) {

        List<BeadingCAR> beadingCars = beadingCarRepo.findByUserId(UserId);
        if (beadingCars.isEmpty()) {
            throw new BeadingCarNotFoundException("No Beading cars found for user with id: " + UserId, HttpStatus.NOT_FOUND);
        }
        List<BeadingCARDto> dtos = new ArrayList<>();
        for (BeadingCAR beadingCAR : beadingCars) {
            dtos.add(new BeadingCARDto(beadingCAR));
        }
        return dtos;
    }

}
