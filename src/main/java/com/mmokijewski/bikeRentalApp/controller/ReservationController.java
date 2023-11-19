package com.mmokijewski.bikeRentalApp.controller;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    ReservationService reservationService;

    @Autowired
    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationDto>> allReservations() {
        return ResponseEntity.ok(this.reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> singleReservation(@PathVariable final Long id) {
        final ReservationDto reservationDto = reservationService.findById(id);
        return reservationDto != null ? ResponseEntity.ok(reservationDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/new/{bikeId}/{cyclistId}")
    public ResponseEntity newReservation(@PathVariable final Long bikeId, @PathVariable final Long cyclistId) {
        try {
            final ReservationDto reservation = reservationService.createReservation(bikeId, cyclistId);
            return ResponseEntity.ok(reservation);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @GetMapping("/new/{bikeId}/{cyclistId}/{minutes}")
    public ResponseEntity newReservation(@PathVariable final Long bikeId, @PathVariable final Long cyclistId,
            @PathVariable final int minutes) {
        try {
            final ReservationDto reservation = reservationService.createReservation(bikeId, cyclistId, minutes);
            return ResponseEntity.ok(reservation);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity cancelReservation(@PathVariable final Long id) {
        try {
            final ReservationDto reservation = reservationService.cancelReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}