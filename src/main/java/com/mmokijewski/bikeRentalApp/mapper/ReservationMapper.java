package com.mmokijewski.bikeRentalApp.mapper;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.entity.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper implements AbstractMapper<ReservationDto, Reservation> {

    @Override
    public ReservationDto mapToDto(final Reservation entity) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, ReservationDto.class);
    }

    @Override
    public Reservation mapToEntity(final ReservationDto dto) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Reservation.class);
    }
}