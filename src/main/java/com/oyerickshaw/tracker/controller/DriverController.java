package com.oyerickshaw.tracker.controller;

import com.oyerickshaw.tracker.dto.DriverDto;
import com.oyerickshaw.tracker.dto.request.AddDriverRequest;
import com.oyerickshaw.tracker.dto.request.RangeRequest;
import com.oyerickshaw.tracker.dto.request.UpdateLocationRequest;
import com.oyerickshaw.tracker.dto.response.RangeResponse;
import com.oyerickshaw.tracker.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private TrackerService trackerService;

    /**
     * Gets Drivers within circular radius of given distance
     * @param radius  (Distance from current coordinates)
     * @param latitude (Latitude of current coordinates)
     * @param longitude (Longitude of current coordinates)
     * @return
     */

    @RequestMapping(value = "/find/range", method = RequestMethod.GET)
    public ResponseEntity<?> getDrivesInRange(
            @RequestParam(name = "radius") BigDecimal radius,
            @RequestParam(name = "latitude") BigDecimal latitude,
            @RequestParam(name = "longitude") BigDecimal longitude
    ){
        return new ResponseEntity<>(trackerService.getDriversInRange(latitude, longitude, radius), HttpStatus.OK);
    }


    /**
     * Updates Location of driver
     * @param  updateLocationRequest  (Contains New Coordinates & driverId)
     * @return
     */
    @RequestMapping(value = "/location/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateDriveLocation(
            @RequestBody UpdateLocationRequest updateLocationRequest
    ){
        return null;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDriver(
            @RequestBody AddDriverRequest request
            ){
        return new ResponseEntity<>(trackerService.addDriver(request), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriver(
            @PathVariable(name="driverId") String driverId
    ){
        return new ResponseEntity<>(trackerService.findDriverById(driverId), HttpStatus.OK);
    }

 }
