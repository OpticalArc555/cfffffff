package com.spring.jwt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InspectorProfileDto {

        private String address;

        private String city;

        private String firstName;

        private String lastName;

        private String email;

        private String mobileNo;
}
