package com.mmokijewski.bikeRentalApp.dto;

import java.util.List;

public class CyclistDto {

    private Long id;
    private String firstName;
    private String lastName;
    private List<ReservationDto> reservations;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(final List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
}