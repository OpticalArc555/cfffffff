package com.spring.jwt.service;

import com.spring.jwt.Interfaces.PlacedBidService;
import com.spring.jwt.dto.BeedingDtos.PlacedBidDTO;
import com.spring.jwt.entity.BidCars;
import com.spring.jwt.entity.PlacedBid;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.BidAmountLessException;
import com.spring.jwt.exception.BidNotFoundExceptions;
import com.spring.jwt.exception.PlacedBidNotFoundExceptions;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.repository.BidCarsRepo;
import com.spring.jwt.repository.PlacedBidRepo;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacedBidServiceImpl implements PlacedBidService {
      private final PlacedBidRepo placedBidRepo;

      private final  BidCarsRepo bidCarsRepo;

      private final ModelMapper modelMapper;

      private final UserRepository userRepository;


    @Override
    public String placeBid(PlacedBidDTO placedBidDTO, Integer bidCarId) throws BidAmountLessException {
        Optional<BidCars> byId = bidCarsRepo.findById(bidCarId);
        if (byId.isEmpty()){
            throw new UserNotFoundExceptions("Bid Cannot Be Placed as Car is Not Found in Our Database");
        }
        PlacedBid placedBid = convertToEntity(placedBidDTO);
        if(placedBid.getAmount()< byId.get().getBasePrice()){

            throw new BidAmountLessException();
        }

          placedBid.setBidCarId(bidCarId);
          placedBidRepo.save(placedBid);

        return "Bid Placed Successfully";
    }

    @Override
    public List<PlacedBidDTO> getByUserId(Integer userId) throws UserNotFoundExceptions {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundExceptions("User not found with ID: " + userId);
        }
        List<PlacedBid> bids = placedBidRepo.findByUserId(userId);
        return bids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlacedBidDTO> getByCarID(Integer bidCarId) throws BidNotFoundExceptions {
        Optional<BidCars> bidCar = bidCarsRepo.findById(bidCarId);
        if (bidCar.isEmpty()) {
            throw new BidNotFoundExceptions("Bid car not found with ID: " + bidCarId);
        }
        List<PlacedBid> bids = placedBidRepo.findByBidCarId(bidCarId);
        return bids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlacedBidDTO getById(Integer placedBidId) throws PlacedBidNotFoundExceptions {
        Optional<PlacedBid> optionalPlacedBid = placedBidRepo.findById(placedBidId);
        if (optionalPlacedBid.isPresent()) {
            PlacedBid placedBid = optionalPlacedBid.get();
            System.out.println(placedBid);
            return convertToDto(placedBid);
        } else {
            throw new PlacedBidNotFoundExceptions("PlacedBid not found with ID: " + placedBidId);
        }
    }

    @Override
    public List<PlacedBidDTO> getTopThree(Integer bidCarId) throws BidNotFoundExceptions {
        Optional<BidCars> bidCar = bidCarsRepo.findById(bidCarId);
        if (bidCar.isEmpty()) {
            throw new BidNotFoundExceptions("Bid car not found with ID: " + bidCarId);
        }
        List<PlacedBid> topThreeBids = placedBidRepo.findTop3ByBidCarIdOrderByAmountDesc(bidCarId);
        return topThreeBids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PlacedBid convertToEntity(PlacedBidDTO placedBidDTO){
        PlacedBid toEntity = modelMapper.map(placedBidDTO, PlacedBid.class);
        return toEntity;
    }

    public PlacedBidDTO convertToDto(PlacedBid placedBid){
        PlacedBidDTO toDto = modelMapper.map(placedBid, PlacedBidDTO.class);
        return toDto;
    }



}
