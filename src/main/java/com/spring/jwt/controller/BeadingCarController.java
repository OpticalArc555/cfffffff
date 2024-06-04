package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.BeadingCarService;
import com.spring.jwt.dto.BeadingCAR.BeadingCARDto;
import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.exception.BeadingCarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/BeadingCarController")
public class BeadingCarController {

    private final BeadingCarService beadingCarService;

    @PostMapping(value = "/carregister")
    public ResponseEntity<ResponseDto> carRegistration(@RequestBody BeadingCARDto beadingCARDto) {
        try{
            String result = beadingCarService.AddBCar(beadingCARDto);
            return (ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success",result)));
        }catch (BeadingCarNotFoundException beadingCarNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","Dealer not found"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBeadingCars() {
        try {
            List<BeadingCARDto> beadingCars = beadingCarService.getAllBeadingCars();
            return ResponseEntity.status(HttpStatus.OK).body(beadingCars);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("error", "An error occurred while retrieving beading cars"));
        }
    }

    @GetMapping("getbyId/{id}")
    public ResponseEntity<?> getBeadingCarById(@PathVariable("id") Integer beadingCarId) {
        try {
            BeadingCARDto beadingCar = beadingCarService.getBCarById(beadingCarId);
            return ResponseEntity.status(HttpStatus.OK).body(beadingCar);
        } catch (BeadingCarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("error", "Beading car not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("error", "An error occurred while retrieving beading car"));
        }
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDto> deleteBeadingCar(@PathVariable("id") Integer beadingCarId) {
        try {
            String result = beadingCarService.deleteBCar(beadingCarId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success", result));
        } catch (BeadingCarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("error", "Beading car not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("error", "An error occurred while deleting beading car"));
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ResponseDto> editCarDetails(@PathVariable("id") Integer beadingCarId, @RequestBody BeadingCARDto beadingCARDto) {
        try {
            String result = beadingCarService.editCarDetails(beadingCARDto, beadingCarId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success", result));
        } catch (BeadingCarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("error", "Beading car not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("error", "An error occurred while editing beading car details"));
        }
    }

    @GetMapping("/getByUserId1/{userId}")
    public ResponseEntity<?> getBeadingCarsByUserId(@PathVariable("userId") int userId) {
        try {
            List<BeadingCARDto> beadingCars = beadingCarService.getByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(beadingCars);
        } catch (BeadingCarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("error", "No Beading cars found for the user"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("error", "An error occurred while retrieving beading cars for the user"));
        }
    }
}
