package com.mmokijewski.bikeRentalApp.controller;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> singleReservation(@PathVariable final String id) {
        final ReservationDto reservationDto = reservationService.findById(Long.parseLong(id));
        return reservationDto != null ? ResponseEntity.ok(reservationDto) : ResponseEntity.notFound().build();
    }
}