package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.BeadingCAR.BeadingCARDto;
import com.spring.jwt.dto.CarDto;
import com.spring.jwt.dto.FilterDto;
import com.spring.jwt.entity.Status;

import java.util.List;

public interface BeadingCarService {

    public String AddBCar(BeadingCARDto beadingCARDto);
    public String editCarDetails(BeadingCARDto beadingCARDto, Integer beadingCarId);
    public List<BeadingCARDto> getAllBeadingCars();
    public String deleteBCar(Integer beadingCarId);

   public BeadingCARDto getBCarById(Integer beadingCarId);

   public List<BeadingCARDto>getByUserId(int UserId);





}
