package com.oyerickshaw.tracker.controller;

import com.oyerickshaw.tracker.dto.request.AddDriverRequest;
import com.oyerickshaw.tracker.dto.response.BaseResponse;
import com.oyerickshaw.tracker.exceptions.ServiceException;
import com.oyerickshaw.tracker.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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


    //400s are already handled by controller
    @RequestMapping(value = "/find/range", method = RequestMethod.GET)
    public ResponseEntity<?> getDrivesInRange(
            @RequestParam(name = "radius", required = true) BigDecimal radius,
            @RequestParam(name = "latitude", required = true) BigDecimal latitude,
            @RequestParam(name = "longitude", required = true) BigDecimal longitude
    ){
        try{
            return new ResponseEntity<>(trackerService.getDriversInRange(latitude, longitude, radius), HttpStatus.OK);
        }catch (ServiceException e){
            return new ResponseEntity<>(new BaseResponse(e.getMessage(), e.getErrorCode()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }


    /**
     * Updates Location of driver
     * @param  updateLocationRequest  (Contains New Coordinates & driverId)
     * @return
     */
    @RequestMapping(value = "/location/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateDriveLocation(
            @RequestBody AddDriverRequest updateLocationRequest
    ){
        if(updateLocationRequest.getDriverId()==null && updateLocationRequest.getDriverId().isEmpty()){
            throw new ServiceException(400, "Invalid driver id");
        }
        try{
            return new ResponseEntity<>(trackerService.addOrUpdateDriver(updateLocationRequest), HttpStatus.CREATED);
        }
        catch (ServiceException e) {
            return new ResponseEntity<>(new BaseResponse(e.getMessage(), e.getErrorCode()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

    /**
     * Add New Driver
     * @param  request  (Contains Initial Coordinates & driver name)
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDriver(
            @RequestBody AddDriverRequest request
            ){
        try{
            return new ResponseEntity<>(trackerService.addOrUpdateDriver(request), HttpStatus.CREATED);
        }
        catch (ServiceException e) {
            return new ResponseEntity<>(new BaseResponse(e.getMessage(), e.getErrorCode()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

    /**
     * Get Driver Details
     * @param driverId
     * @return
     */
    @RequestMapping(value = "/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriver(
            @PathVariable(name="driverId") String driverId
    ){
        try{
            return new ResponseEntity<>(trackerService.findDriverById(driverId), HttpStatus.OK);
        }catch (ServiceException e) {
            return new ResponseEntity<>(new BaseResponse(e.getMessage(), e.getErrorCode()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }


    /**
     * Get All Driver Locations
     * (Check SchedulerTasks.java for implementation with Websocket)
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDrivers(){
        return new ResponseEntity<>(trackerService.getAllDrivers(), HttpStatus.OK);
    }

 }
