package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.FilterService;
import com.spring.jwt.Interfaces.ICarRegister;
import com.spring.jwt.Interfaces.PendingBookingService;
import com.spring.jwt.Interfaces.UserService;
import com.spring.jwt.dto.*;
import com.spring.jwt.exception.CarNotFoundException;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.exception.UserNotFoundExceptions;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class FilterController {
    @Autowired
    private final FilterService filterService;

    @Autowired
    private UserService userService;
    @Autowired
    private ICarRegister iCarRegister;

    private PendingBookingService pendingBookingService;


    @GetMapping("/mainFilter")
    public ResponseEntity<ResponseAllCarDto> searchByFilter(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) String fuelType) {

        Integer convertedYear = year != null && !year.isEmpty() ? Integer.valueOf(year) : null;

        FilterDto filterDto = new FilterDto(minPrice, maxPrice, area, brand, model, transmission, fuelType, convertedYear);

        try {
            List<CarDto> listOfCar = filterService.searchByFilter(filterDto);
            ResponseAllCarDto responseAllCarDto = new ResponseAllCarDto("success");
            responseAllCarDto.setList(listOfCar);
            return ResponseEntity.status(HttpStatus.OK).body(responseAllCarDto);
        } catch (PageNotFoundException pageNotFoundException) {
            ResponseAllCarDto responseAllCarDto = new ResponseAllCarDto("unsuccess");
            responseAllCarDto.setException("page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllCarDto);
        }
    }

    @GetMapping("/searchBarFilter")
    public ResponseEntity<?> searchBarFilter(@RequestParam String searchBarInput) {
        try {
            List<CarDto> listOfJob = filterService.searchBarFilter(searchBarInput);
            ResponseAllCarDto responseGetAllJobDto = new ResponseAllCarDto("success");
            responseGetAllJobDto.setList(listOfJob);
            return ResponseEntity.status(HttpStatus.OK).body(responseGetAllJobDto);
        } catch (PageNotFoundException pageNotFoundException) {
            ResponseAllCarDto responseGetAllJobDto = new ResponseAllCarDto("unsuccess");
            responseGetAllJobDto.setException(String.valueOf(pageNotFoundException));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseGetAllJobDto);
        }
    }


    /**
     * Retrieves a single car by its ID.
     *
     * @param carId The ID of the car to retrieve.
     * @return ResponseEntity containing the response for the request.
     *         If the car is found, the response will have a status of OK (200)
     *         and the car details will be included in the body.
     *         If the car is not found, the response will have a status of NOT_FOUND (404)
     *         and an error message will be included in the body.
     */
    @GetMapping("/getCar")
    public ResponseEntity<ResponseSingleCarDto> findByArea(@RequestParam int carId) {
        try {
            ResponseSingleCarDto responseSingleCarDto = new ResponseSingleCarDto("success");

            CarDto car = iCarRegister.findById(carId);

            responseSingleCarDto.setObject(car);
            return ResponseEntity.status(HttpStatus.OK).body(responseSingleCarDto);
        }catch (CarNotFoundException carNotFoundException){
            ResponseSingleCarDto responseSingleCarDto = new ResponseSingleCarDto("unsuccess");
            responseSingleCarDto.setException("car not found by car id");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseSingleCarDto);
        }

//        return ResponseEntity.ok(cars.get());*
    }
    @GetMapping("/getAllCars")
    public ResponseEntity<?> getAllCars(@RequestParam int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<CarDto> listOfCar = iCarRegister.getAllCarsWithPages(pageNo, pageSize);

            ResponseAllCarDto responseAllCarDto = new ResponseAllCarDto("success");
            responseAllCarDto.setList(listOfCar);

            return ResponseEntity.status(HttpStatus.OK).body(responseAllCarDto);
        } catch (CarNotFoundException carNotFoundException) {
            ResponseAllCarDto responseAllCarDto = new ResponseAllCarDto("unsuccessful");
            responseAllCarDto.setException("Car not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllCarDto);
        } catch (PageNotFoundException pageNotFoundException) {
            ResponseAllCarDto responseAllCarDto = new ResponseAllCarDto("unsuccessful");
            responseAllCarDto.setException("Page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllCarDto);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto> forgotPass(HttpServletRequest request) throws UserNotFoundExceptions {
        try {

            String email = request.getParameter("email");

            String token = RandomStringUtils.randomAlphabetic(40);

            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(1);

            userService.updateResetPassword(token, email);

            String resetPasswordLink = "http://localhost:5173/reset-password?token=" + token;


            ResponseDto response = userService.forgotPass(email, resetPasswordLink, request.getServerName());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Successful", response.getMessage()));
        } catch (UserNotFoundExceptions e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("Unsuccessful", "Invalid email. Please register."));
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody ResetPassword resetPassword) throws UserNotFoundExceptions {

        try {
            String token = resetPassword.getToken();
            String newPassword = resetPassword.getPassword();
            String newPassword1 = resetPassword.getConfirmPassword();

            if (newPassword.equals(newPassword1)) {

                ResponseDto response = userService.updatePassword(token, newPassword);

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Successful", response.getMessage()));
            }
            else{
                return new ResponseEntity<ResponseDto>(HttpStatus.NOT_FOUND);
            }
        }

        catch (UserNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("Unsuccessful", "Something went wrong"));
        }
    }

//    @GetMapping("/reset-password")
//    public ResponseEntity<String> resetPasswordPage(@RequestParam(name = "token") String token) {
//        try {
//            ClassPathResource resource = new ClassPathResource("templates/reset-password.html");
//            String htmlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())), StandardCharsets.UTF_8);
//            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading HTML file");
//        }
//    }
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String query) {
        List<String> suggestions = iCarRegister.getAutocompleteSuggestions(query);
        return ResponseEntity.ok(suggestions);
    }

}