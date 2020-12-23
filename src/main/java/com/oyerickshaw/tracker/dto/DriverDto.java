package com.oyerickshaw.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
    private String driverId;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
