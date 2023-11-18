package com.mmokijewski.bikeRentalApp.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.dto.CyclistDto;
import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;
import com.mmokijewski.bikeRentalApp.mapper.BikeMapper;
import com.mmokijewski.bikeRentalApp.mapper.CyclistMapper;
import com.mmokijewski.bikeRentalApp.mapper.ReservationMapper;
import com.mmokijewski.bikeRentalApp.repository.ReservationRepository;
import com.mmokijewski.bikeRentalApp.service.ReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private static final Logger LOGGER = LogManager.getLogger(ReservationService.class);
    private static final int DEFAULT_RESERVATION_TIME_IN_MINUTES = 10;

    private final BikeMapper bikeMapper;
    private final CyclistMapper cyclistMapper;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationServiceImpl(final BikeMapper bikeMapper, final CyclistMapper cyclistMapper,
            final ReservationRepository reservationRepository, final ReservationMapper reservationMapper) {
        this.bikeMapper = bikeMapper;
        this.cyclistMapper = cyclistMapper;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationDto> getAllReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        LOGGER.info("Found: {} reservation(s)", reservations.size());
        return reservationMapper.mapToDtos(reservations);
    }

    public ReservationDto findById(final Long id) {
        final Optional<Reservation> reservation = this.reservationRepository.findById(id);
        return reservation.map(this.reservationMapper::mapToDto).orElse(null);
    }

    public Reservation createReservation(final BikeDto bike, final CyclistDto cyclist)
            throws BikeNotAvailableException {

        if (checkIfBikeAvailable(bike)) {
            final Reservation newReservation =
                    new Reservation(cyclistMapper.mapToEntity(cyclist), bikeMapper.mapToEntity(bike),
                            LocalDateTime.now(),
                            LocalDateTime.now().plus(DEFAULT_RESERVATION_TIME_IN_MINUTES, ChronoUnit.MINUTES));
            newReservation.setId(10L);
            reservationRepository.saveAndFlush(newReservation);
            return newReservation;
        } else {
            throw new BikeNotAvailableException(bike.getId().toString());
        }
    }

    public Reservation createReservation(final BikeDto bike, final CyclistDto cyclist, final int minutes)
            throws BikeNotAvailableException {

        if (checkIfBikeAvailable(bike)) {
            final Reservation newReservation =
                    new Reservation(cyclistMapper.mapToEntity(cyclist), bikeMapper.mapToEntity(bike),
                            LocalDateTime.now(), LocalDateTime.now().plus(minutes, ChronoUnit.MINUTES));
            reservationRepository.saveAndFlush(newReservation);
            return newReservation;
        } else {
            throw new BikeNotAvailableException(bike.getId().toString());
        }
    }

    private boolean checkIfBikeAvailable(final BikeDto bike) {
        boolean available = true;
        for (final Reservation reservation : reservationRepository.findAll()) {
            if (reservation.getBike().getId().equals(bike.getId()) && reservation.getEndTime()
                    .isAfter(LocalDateTime.now())) {
                available = false;
                break;
            }
        }
        return available;
    }
}