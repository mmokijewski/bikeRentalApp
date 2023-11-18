package com.mmokijewski.bikeRentalApp.controller;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.CyclistDto;
import com.mmokijewski.bikeRentalApp.service.CyclistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cyclists")
public class CyclistController {

    CyclistService cyclistService;

    @Autowired
    public CyclistController(final CyclistService cyclistService) {
        this.cyclistService = cyclistService;
    }

    @GetMapping()
    public ResponseEntity<List<CyclistDto>> allCyclists() {
        return ResponseEntity.ok(this.cyclistService.getAllCyclists());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CyclistDto> singleCyclist(@PathVariable final Long id) {
        final CyclistDto cyclistDto = cyclistService.findById(id);
        return cyclistDto != null ? ResponseEntity.ok(cyclistDto) : ResponseEntity.notFound().build();
    }
}