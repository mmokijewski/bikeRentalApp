package com.mmokijewski.bikeRentalApp.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

@Entity
public class Reservation extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "CYCLIST_ID", nullable = false)
    private Cyclist cyclist;
    @ManyToOne
    @JoinColumn(name = "BIKE_ID", nullable = false, updatable = false)
    private Bike bike;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;

    public Reservation(final Bike bike, final Cyclist cyclist, final LocalDateTime startDate,
            final LocalDateTime endDate) {
        this.bike = bike;
        this.cyclist = cyclist;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservation() {
    }

    public Cyclist getCyclist() {
        return cyclist;
    }

    public void setCyclist(final Cyclist cyclist) {
        this.cyclist = cyclist;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(final Bike bike) {
        this.bike = bike;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }
}