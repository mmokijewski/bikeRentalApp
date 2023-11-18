package com.mmokijewski.bikeRentalApp.service.impl;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.entity.Bike;
import com.mmokijewski.bikeRentalApp.mapper.BikeMapper;
import com.mmokijewski.bikeRentalApp.repository.BikeRepository;
import com.mmokijewski.bikeRentalApp.service.BikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BikeServiceImpl implements BikeService {

    private static final Logger LOGGER = LogManager.getLogger(BikeServiceImpl.class);

    private final BikeRepository bikeRepository;

    private final BikeMapper bikeMapper;

    @Autowired
    public BikeServiceImpl(final BikeRepository bikeRepository, final BikeMapper bikeMapper) {
        this.bikeRepository = bikeRepository;
        this.bikeMapper = bikeMapper;
    }

    @Override
    public List<BikeDto> getAllBikes() {
        final List<Bike> bikes = this.bikeRepository.findAll();
        LOGGER.info("Found: {} bike(s)", bikes.size());
        return this.bikeMapper.mapToDtos(bikes);
    }

    @Override
    public BikeDto findById(final Long id) {
        return this.bikeMapper.mapToDto(this.bikeRepository.findById(id).get());
    }
}