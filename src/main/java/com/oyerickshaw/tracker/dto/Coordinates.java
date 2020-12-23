package com.oyerickshaw.tracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
