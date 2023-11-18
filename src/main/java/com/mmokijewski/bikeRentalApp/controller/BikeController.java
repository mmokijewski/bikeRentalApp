package com.mmokijewski.bikeRentalApp.controller;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<BikeDto> singleBike(@PathVariable final String id) {
        final BikeDto bikeDto = bikeService.findById(Long.parseLong(id));
        return bikeDto != null ? ResponseEntity.ok(bikeDto) : ResponseEntity.notFound().build();
    }
}