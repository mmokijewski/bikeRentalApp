package com.mmokijewski.bikeRentalApp.dto;

import java.time.LocalDateTime;

public class ReservationDto {

    private Long id;
    private BikeDto bike;
    private CyclistDto cyclist;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean cancelled;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BikeDto getBike() {
        return bike;
    }

    public void setBike(final BikeDto bike) {
        this.bike = bike;
    }

    public CyclistDto getCyclist() {
        return cyclist;
    }

    public void setCyclist(final CyclistDto cyclist) {
        this.cyclist = cyclist;
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

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}