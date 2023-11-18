package com.mmokijewski.bikeRentalApp.dto;

public class BikeDto {

    private Long id;
    private String brand;

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
}