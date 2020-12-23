package com.oyerickshaw.tracker.service;

import com.oyerickshaw.tracker.dto.DriverDto;
import com.oyerickshaw.tracker.dto.request.AddDriverRequest;
import com.oyerickshaw.tracker.dto.response.DriverResponse;
import com.oyerickshaw.tracker.dto.response.RangeResponse;
import com.oyerickshaw.tracker.entity.Driver;
import com.oyerickshaw.tracker.exceptions.ServiceException;
import com.oyerickshaw.tracker.repository.DriverRepository;
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

    public DriverResponse addDriver(AddDriverRequest addDriverRequest){
        Driver driver  = dozerBeanMapper.map(addDriverRequest, Driver.class);
        Driver res = driverRepository.save(driver);
        return dozerBeanMapper.map(res, DriverResponse.class);
    }

    public DriverResponse findDriverById(String driverId){
        Optional<Driver> driver = driverRepository.findById(driverId);
        if(!driver.isPresent()){
            throw new ServiceException(404, "Not found");
        }
        return dozerBeanMapper.map(driver.get(), DriverResponse.class);
    }

    public RangeResponse getDriversInRange(BigDecimal latitude, BigDecimal longitude, BigDecimal radius){
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

    public List<DriverDto> getAllDrivers(){
        List<DriverDto> res = new ArrayList<>();
        driverRepository.findAll().forEach(driver -> {
            res.add(dozerBeanMapper.map(driver, DriverDto.class));
        });

        return res;
    }

}
