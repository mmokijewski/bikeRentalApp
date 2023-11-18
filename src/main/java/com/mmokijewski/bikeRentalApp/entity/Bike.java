package com.mmokijewski.bikeRentalApp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

@Entity
public class Bike extends AbstractEntity {

    @NotNull
    private String brand;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BIKE_ID")
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }
}