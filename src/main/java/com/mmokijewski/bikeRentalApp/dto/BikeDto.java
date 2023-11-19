package com.mmokijewski.bikeRentalApp.dto;

import com.mmokijewski.bikeRentalApp.enums.BikeStatus;

public class BikeDto {

    private Long id;
    private String brand;
    private BikeStatus status;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public BikeStatus getStatus() {
        return status;
    }

    public void setStatus(final BikeStatus status) {
        this.status = status;
    }
}