package com.mmokijewski.bikeRentalApp.mapper;

import com.mmokijewski.bikeRentalApp.dto.CyclistDto;
import com.mmokijewski.bikeRentalApp.entity.Cyclist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CyclistMapper implements AbstractMapper<CyclistDto, Cyclist> {

    @Override
    public CyclistDto mapToDto(final Cyclist entity) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, CyclistDto.class);
    }

    @Override
    public Cyclist mapToEntity(final CyclistDto dto) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cyclist.class);
    }
}