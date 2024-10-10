package com.spring.jwt.Bidding.DTO;

import lombok.Data;
@Data
public class SingleSalesPersonDto {

        private String status;
        private SalesPersonDto Response;

        public SingleSalesPersonDto(String status) {
            this.status = status;
        }

    }

