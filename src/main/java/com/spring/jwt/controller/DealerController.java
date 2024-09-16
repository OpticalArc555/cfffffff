package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.DealerService;
import com.spring.jwt.dto.*;
import com.spring.jwt.dto.DealerResponseDtos.DealerStatusDto;
import com.spring.jwt.exception.*;
import com.spring.jwt.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/dealer")
@RequiredArgsConstructor
public class DealerController {

    @Value("${app.secret-key}")
    private String secretKey;

    private final DataSource dataSource;

    private final DealerService dealerService;

    @PutMapping("/updateDealer/{userId}")
    public ResponseEntity<ResponseDto> updateDealer(@PathVariable("userId") Integer userId, @RequestBody RegisterDto registerDto) {
        try{

            BaseResponseDTO baseResponseDTO = dealerService.updateDealer(userId, registerDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success",baseResponseDTO.getMessage()));

        }
        catch (DealerDeatilsNotFoundException dealerDeatilsNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","dealer details not found exception"));

        }
        catch (UserNotDealerException userNotDealerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","user not a dealer Exception"));

        }
        catch (UserNotFoundExceptions userNotFoundExceptions){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","user not found exception"));
        }
        catch (DuplicateRecordException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto("unsuccess", e.getMessage()));
        }
    }

    @GetMapping("/allDealers/{pageNo}")
    public ResponseEntity<ResponseAllDealerDto> getAllDealers(@PathVariable int pageNo) {
        try{
            System.out.println("1");
            List<DealerDto> dealers = dealerService.getAllDealers(pageNo);
            System.out.println("2");

            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("success");
            System.out.println("3");

            responseAllDealerDto.setList(dealers);
            return ResponseEntity.status(HttpStatus.OK).body(responseAllDealerDto);
        }catch (DealerNotFoundException dealerNotFoundException){
            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("unsuccess");
            responseAllDealerDto.setException("Dealer not found by id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllDealerDto);
        }catch (PageNotFoundException pageNotFoundException){
            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("unsuccess");
            responseAllDealerDto.setException("page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllDealerDto);

        }
    }


    @GetMapping("/{dealerId}")
    public ResponseEntity<DealerResponseForSingleDealerDto> getDealerById(@PathVariable("dealerId") Integer dealerId) {
       try{

           DealerDto dealer = dealerService.getDealerById(dealerId);
           DealerResponseForSingleDealerDto dealerResponseForSingleDealerDto = new DealerResponseForSingleDealerDto("success");
           dealerResponseForSingleDealerDto.setDealerDto(dealer);

           return ResponseEntity.status(HttpStatus.OK).body(dealerResponseForSingleDealerDto);
       }
       catch (DealerNotFoundException dealerNotFoundException){
           DealerResponseForSingleDealerDto dealerResponseForSingleDealerDto = new DealerResponseForSingleDealerDto("unsuccess");
           dealerResponseForSingleDealerDto.setException("dealer not found by id");

           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dealerResponseForSingleDealerDto);
       }
    }

    @GetMapping("/win/{userId}")
    public ResponseEntity<DealerDto> getDealerByUserId(@PathVariable Integer userId) {
        DealerDto dealerDto = dealerService.getDealerByUserId(userId);
        return ResponseEntity.ok(dealerDto);
    }


    @DeleteMapping("/delete/{dealerId}")
    public ResponseEntity<ResponseDto> deleteDealer(@PathVariable("dealerId") Integer dealerId) {
       try{
            BaseResponseDTO baseResponseDTO = dealerService.deleteDealer(dealerId);
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success",baseResponseDTO.getMessage()));

       }catch (DealerNotFoundException dealerNotFoundException) {

           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","dealer not found by id"));

       }
    }


    @PutMapping("/changePassword/{dealerId}")
    public ResponseEntity<ResponseDto> changePassword(
            @PathVariable("dealerId") Integer dealerId,
            @RequestBody ChangePasswordDto changePasswordDto
    ) {
        try{
            BaseResponseDTO baseResponseDTO =dealerService.changePassword(dealerId, changePasswordDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("success",baseResponseDTO.getMessage()));

        }catch (NewAndOldPasswordDoseNotMatchException newAndOldPasswordDoseNotMatchException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","New And Old Password Dose Not Match"));

        }catch(OldNewPasswordMustBeDifferentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess",e.getMessage()));
        }
        catch (InvalidOldPasswordException invalidOldPasswordException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","Invalid Old Password "));


        }catch (UserNotDealerException userNotDealerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","User Not a Dealer"));


        }catch (UserNotFoundExceptions userNotFoundExceptions){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("unsuccess","User Not Found"));

        }
    }
    @GetMapping("/getDealerId")
    public ResponseEntity<?> getDealerId(@RequestParam String email){

        try
        {
            int id=dealerService.getDealerIdByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (EmailNotExistException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email Not Exist Exception");

        }
    }

    @GetMapping ("/getAllDealersBySalesPersonID")
    public ResponseEntity<ResponseAllDealerDto> getDealerSalePerson (@RequestParam Integer salesPersonID){
        try {
            List<DealerDto> dealers = dealerService.getAllDealersBySalesPersonId(salesPersonID);
            Integer size= dealers.size();
            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("success");
            responseAllDealerDto.setTotalDealers(size);
            responseAllDealerDto.setList(dealers);
            return ResponseEntity.status(HttpStatus.OK).body(responseAllDealerDto);
        } catch (DealerNotFoundException dealerNotFoundException) {
            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("unsuccess");
            responseAllDealerDto.setException("Dealer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllDealerDto);
        }
    }

    @DeleteMapping("/refreshCachedData")
    public ResponseEntity<String> deleteRefreshCachedData (@RequestHeader("secret-key") String providedSecretKey)  {
        if (!secretKey.equals(providedSecretKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid secret key");
        }

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             Statement dropStmt = connection.createStatement()) {

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            try (ResultSet resultSet = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE()")) {

                while (resultSet.next()) {
                    String tableName = resultSet.getString(1);
                    dropStmt.executeUpdate("DROP TABLE IF EXISTS `" + tableName + "`");
                }
            }

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

            return ResponseEntity.ok("Data Refreshed successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to drop database: " + e.getMessage());
        }
    }


    @PatchMapping("/statusUpdate")
    public ResponseEntity<DealerStatusDto> dealerStatusUpdate(@RequestParam Integer dealerId, @RequestParam Boolean status) {
        try {
            dealerService.updateStatus(dealerId, status);

            DealerStatusDto dealerStatusDto = new DealerStatusDto("success");
            dealerStatusDto.setMessage("dealer status updated");

            return ResponseEntity.status(HttpStatus.OK).body(dealerStatusDto);
        } catch (DealerNotFoundException dealerNotFoundException) {
            DealerStatusDto dealerStatusDto = new DealerStatusDto("unsuccess");
            dealerStatusDto.setException(String.valueOf(dealerNotFoundException));

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dealerStatusDto);

        }
    }
    @GetMapping("/allDealer")
    public ResponseEntity<ResponseAllDealerDto> getAllDealers() {
        try {
            List<DealerDto> dealers = dealerService.getAllDealer();
            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("success");
            responseAllDealerDto.setList(dealers);
            return ResponseEntity.status(HttpStatus.OK).body(responseAllDealerDto);
        } catch (DealerNotFoundException dealerNotFoundException) {
            ResponseAllDealerDto responseAllDealerDto = new ResponseAllDealerDto("unsuccess");
            responseAllDealerDto.setException("Dealer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllDealerDto);
        }
    }
}
