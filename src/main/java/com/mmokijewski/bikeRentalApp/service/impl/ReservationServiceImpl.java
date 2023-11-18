package com.mmokijewski.bikeRentalApp.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.entity.Bike;
import com.mmokijewski.bikeRentalApp.entity.Cyclist;
import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;
import com.mmokijewski.bikeRentalApp.mapper.ReservationMapper;
import com.mmokijewski.bikeRentalApp.repository.BikeRepository;
import com.mmokijewski.bikeRentalApp.repository.CyclistRepository;
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

    private final ReservationMapper reservationMapper;
    private final BikeRepository bikeRepository;
    private final CyclistRepository cyclistRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(final ReservationMapper reservationMapper, final BikeRepository bikeRepository,
            final CyclistRepository cyclistRepository, final ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.bikeRepository = bikeRepository;
        this.cyclistRepository = cyclistRepository;
        this.reservationRepository = reservationRepository;
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

    public ReservationDto createReservation(final Long bikeId, final Long cyclistId) throws BikeNotAvailableException {
        final Bike bike = bikeRepository.findById(bikeId).get();
        if (checkIfBikeAvailable(bike)) {
            final Cyclist cyclist = cyclistRepository.findById(cyclistId).get();
            final Reservation newReservation = new Reservation(bike, cyclist, LocalDateTime.now(),
                    LocalDateTime.now().plus(DEFAULT_RESERVATION_TIME_IN_MINUTES, ChronoUnit.MINUTES));
            reservationRepository.saveAndFlush(newReservation);
            return reservationMapper.mapToDto(newReservation);
        } else {
            throw new BikeNotAvailableException(bike.getId().toString());
        }
    }

    public ReservationDto createReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException {

        final Bike bike = bikeRepository.findById(bikeId).get();
        if (checkIfBikeAvailable(bike)) {
            final Cyclist cyclist = cyclistRepository.findById(cyclistId).get();
            final Reservation newReservation = new Reservation(bike, cyclist, LocalDateTime.now(),
                    LocalDateTime.now().plus(minutes, ChronoUnit.MINUTES));
            reservationRepository.saveAndFlush(newReservation);
            return reservationMapper.mapToDto(newReservation);
        } else {
            throw new BikeNotAvailableException(bike.getId().toString());
        }
    }

    private boolean checkIfBikeAvailable(final Bike bike) {
        boolean available = true;
        for (final Reservation reservation : reservationRepository.findAll()) {
            if (reservation.getBike().getId().equals(bike.getId()) && reservation.getEndDate()
                    .isAfter(LocalDateTime.now())) {
                available = false;
                break;
            }
        }
        return available;
    }
}