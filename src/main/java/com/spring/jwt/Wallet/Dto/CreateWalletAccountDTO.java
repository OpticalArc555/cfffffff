package com.spring.jwt.Wallet.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletAccountDTO {
    private String panCard;
    private int userId;
    private String status = "active";
    private double openingBalance = 0.0;
}
