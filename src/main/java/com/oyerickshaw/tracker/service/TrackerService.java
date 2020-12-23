package com.oyerickshaw.tracker.service;

import com.oyerickshaw.tracker.dto.DriverDto;
import com.oyerickshaw.tracker.dto.request.AddDriverRequest;
import com.oyerickshaw.tracker.dto.response.DriverResponse;
import com.oyerickshaw.tracker.dto.response.RangeResponse;
import com.oyerickshaw.tracker.entity.Driver;
import com.oyerickshaw.tracker.exceptions.ServiceException;
import com.oyerickshaw.tracker.repository.DriverRepository;
import com.oyerickshaw.tracker.utils.Utils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackerService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    /**
     * Makes DB Call to store/update driver details
     */
    public DriverResponse addOrUpdateDriver(AddDriverRequest addDriverRequest){
        if(!Utils.validCoordinates(addDriverRequest.getLatitude(), addDriverRequest.getLongitude())){
            throw new ServiceException(400, "Invalid Coordinates");
        }
        try{
            Driver driver  = dozerBeanMapper.map(addDriverRequest, Driver.class);
            Driver res = driverRepository.save(driver);
            return dozerBeanMapper.map(res, DriverResponse.class);
        }catch (Exception e){
            throw new ServiceException(500, "Falied to write databse.");
        }
    }

    /**
     * Get Driver by Id from DB
     */
    public DriverResponse findDriverById(String driverId){
        Optional<Driver> driver = driverRepository.findById(driverId);
        if(!driver.isPresent()){
            throw new ServiceException(404, "Not found");
        }
        return dozerBeanMapper.map(driver.get(), DriverResponse.class);
    }

    /**
     * Executes spatial query on DB getting drivers within 'radius'
     *
     * Pagination is not handled currently. It should be in ideal scenario.
     */
    public RangeResponse getDriversInRange(BigDecimal latitude, BigDecimal longitude, BigDecimal radius){
        if(!Utils.validCoordinates(latitude, longitude)){
            throw new ServiceException(400, "Invalid Coordinates");
        }

        List<Driver> drivers = driverRepository.getAllDriversInRange(latitude, longitude, radius);

        List<DriverDto> driverDtos = new ArrayList<>();
        for(Driver driver : drivers){
            try{
                driverDtos.add(dozerBeanMapper.map(driver, DriverDto.class));
            }catch (Exception e){
                continue;
            }
        }

        return new RangeResponse(driverDtos);
    }

    /**
     * Query All drivers
     *
     * Pagination is not handled currently. It should be in ideal scenario.
     */
    public List<DriverDto> getAllDrivers(){
        List<DriverDto> res = new ArrayList<>();
        driverRepository.findAll().forEach(driver -> {
            res.add(dozerBeanMapper.map(driver, DriverDto.class));
        });

        return res;
    }

}
