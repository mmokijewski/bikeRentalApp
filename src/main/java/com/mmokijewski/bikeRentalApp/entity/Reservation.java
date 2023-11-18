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
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

    public Reservation(final Cyclist cyclist, final Bike bike, final LocalDateTime startTime,
            final LocalDateTime endTime) {
        this.cyclist = cyclist;
        this.bike = bike;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(final LocalDateTime endTime) {
        this.endTime = endTime;
    }
}