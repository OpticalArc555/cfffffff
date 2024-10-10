package com.spring.jwt.B2B;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class B2bResponse {

        private String status;
        private String message;
        private B2BDto response;


        public B2bResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

    }
