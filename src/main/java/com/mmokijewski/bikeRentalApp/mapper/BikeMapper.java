package com.mmokijewski.bikeRentalApp.mapper;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.entity.Bike;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BikeMapper implements AbstractMapper<BikeDto, Bike> {

    @Override
    public BikeDto mapToDto(final Bike entity) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, BikeDto.class);
    }

    @Override
    public Bike mapToEntity(final BikeDto dto) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Bike.class);
    }
}