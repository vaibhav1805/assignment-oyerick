package com.oyerickshaw.tracker.repository;

import com.oyerickshaw.tracker.entity.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, String> {
    @Query("select d from Driver d where ST_Distance_Sphere(POINT(d.latitude, d.longitude), POINT(:latitude, :longitude)) <= :radius")
    List<Driver> getAllDriversInRange(@Param("latitude") BigDecimal latitude , @Param("longitude") BigDecimal longitude,
                                      @Param("radius") BigDecimal radius);
}
