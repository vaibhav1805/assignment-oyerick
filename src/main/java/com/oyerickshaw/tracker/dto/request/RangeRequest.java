package com.oyerickshaw.tracker.dto.request;

import com.oyerickshaw.tracker.dto.Coordinates;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RangeRequest {
    private Coordinates coordinates;
    private BigDecimal radius;
}
