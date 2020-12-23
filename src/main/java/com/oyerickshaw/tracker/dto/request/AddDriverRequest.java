package com.oyerickshaw.tracker.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddDriverRequest {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
