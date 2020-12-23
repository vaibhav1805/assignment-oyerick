package com.oyerickshaw.tracker.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Driver {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String driverId;

    private String name;

    @Column(nullable= false, precision=10, scale=7)
    private BigDecimal latitude;

    @Column(nullable= false, precision=10, scale=7)
    private BigDecimal longitude;
}
