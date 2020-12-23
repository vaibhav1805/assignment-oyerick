package com.oyerickshaw.tracker.dto.response;

import com.oyerickshaw.tracker.dto.DriverDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeResponse {
    List<DriverDto> drivers;
}
