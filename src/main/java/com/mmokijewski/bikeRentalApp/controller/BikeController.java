package com.mmokijewski.bikeRentalApp.controller;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    BikeService bikeService;

    @Autowired
    public BikeController(final BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping()
    public ResponseEntity<List<BikeDto>> allBikes() {
        return ResponseEntity.ok(this.bikeService.getAllBikes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeDto> singleBike(@PathVariable final Long id) {
        final BikeDto bikeDto = bikeService.findById(id);
        return bikeDto != null ? ResponseEntity.ok(bikeDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/sendToService")
    public ResponseEntity sendToService(@PathVariable final Long id) {
        try {
            final BikeDto bike = bikeService.sendToService(id);
            return ResponseEntity.ok(bike);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}