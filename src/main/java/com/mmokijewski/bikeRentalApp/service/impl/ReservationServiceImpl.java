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
import com.mmokijewski.bikeRentalApp.exception.NoSuchBikeException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchCyclistException;
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

    public ReservationDto createReservation(final Long bikeId, final Long cyclistId)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        return createNewReservation(bikeId, cyclistId, DEFAULT_RESERVATION_TIME_IN_MINUTES);
    }

    public ReservationDto createReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        return createNewReservation(bikeId, cyclistId, minutes);
    }

    private ReservationDto createNewReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        final Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new NoSuchBikeException(bikeId));
        final Cyclist cyclist =
                cyclistRepository.findById(cyclistId).orElseThrow(() -> new NoSuchCyclistException(cyclistId));
        if (checkIfBikeAvailable(bike)) {
            final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            final Reservation newReservation =
                    new Reservation(bike, cyclist, now, now.plus(minutes, ChronoUnit.MINUTES));
            final Reservation saveResult = reservationRepository.saveAndFlush(newReservation);
            return reservationMapper.mapToDto(saveResult);
        } else {
            throw new BikeNotAvailableException(bike.getId());
        }
    }

    private boolean checkIfBikeAvailable(final Bike bike) {
        final Reservation lastReservation = reservationRepository.findLastReservationByBikeId(bike.getId());
        LOGGER.info(lastReservation.getId());
        return lastReservation.getEndDate().isBefore(LocalDateTime.now());
    }
}