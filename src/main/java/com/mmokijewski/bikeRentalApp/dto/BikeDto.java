package com.mmokijewski.bikeRentalApp.dto;

import java.util.List;

public class BikeDto {

    private Long id;
    private String brand;
    private List<ReservationDto> reservations;

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

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(final List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
}