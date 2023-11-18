package com.mmokijewski.bikeRentalApp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

@Entity
public class Cyclist extends AbstractEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CYCLIST_ID")
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(final List<Reservation> reservations) {
        this.reservations = reservations;
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
}