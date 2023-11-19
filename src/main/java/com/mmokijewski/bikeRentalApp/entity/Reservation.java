package com.mmokijewski.bikeRentalApp.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.mmokijewski.bikeRentalApp.enums.ReservationStatus;
import com.sun.istack.NotNull;

@NamedQueries({
        @NamedQuery(name = Reservation.FIND_LAST_RESERVATION_BY_BIKE_ID,
            query = "SELECT r FROM Reservation r JOIN r.bike b WHERE b.id = :bikeId AND r.endDate = " +
                    "(SELECT MAX(res.endDate) FROM Reservation res JOIN res.bike bi WHERE bi.id = :bikeId)"),

        @NamedQuery(name = Reservation.FIND_LAST_RESERVATION,
            query = "SELECT r FROM Reservation r WHERE r.endDate = (SELECT MAX(res.endDate) FROM Reservation res)")
})

@Entity
public class Reservation extends AbstractEntity {

    public static final String FIND_LAST_RESERVATION = "Reservation.findLastReservation";
    public static final String FIND_LAST_RESERVATION_BY_BIKE_ID = "Reservation.findLastReservationByBikeId";

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
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation(final Bike bike, final Cyclist cyclist, final LocalDateTime startDate,
            final LocalDateTime endDate) {
        this.bike = bike;
        this.cyclist = cyclist;
        this.setCreateDate(startDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.ACTIVE;
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(final ReservationStatus status) {
        this.status = status;
    }
}