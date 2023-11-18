package com.mmokijewski.bikeRentalApp.service.impl;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.CyclistDto;
import com.mmokijewski.bikeRentalApp.entity.Cyclist;
import com.mmokijewski.bikeRentalApp.mapper.CyclistMapper;
import com.mmokijewski.bikeRentalApp.repository.CyclistRepository;
import com.mmokijewski.bikeRentalApp.service.CyclistService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CyclistServiceImpl implements CyclistService {

    private static final Logger LOGGER = LogManager.getLogger(CyclistServiceImpl.class);

    private final CyclistRepository cyclistRepository;

    private final CyclistMapper cyclistMapper;

    @Autowired
    public CyclistServiceImpl(final CyclistRepository cyclistRepository, final CyclistMapper cyclistMapper) {
        this.cyclistRepository = cyclistRepository;
        this.cyclistMapper = cyclistMapper;
    }

    @Override
    public List<CyclistDto> getAllCyclists() {
        final List<Cyclist> cyclists = this.cyclistRepository.findAll();
        LOGGER.info("Found: {} bike(s)", cyclists.size());
        return this.cyclistMapper.mapToDtos(cyclists);
    }

    @Override
    public CyclistDto findById(final Long id) {
        return this.cyclistMapper.mapToDto(this.cyclistRepository.findById(id).get());
    }
}