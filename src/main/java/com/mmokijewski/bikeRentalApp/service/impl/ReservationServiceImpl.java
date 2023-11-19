package com.mmokijewski.bikeRentalApp.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.entity.Bike;
import com.mmokijewski.bikeRentalApp.entity.Cyclist;
import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.enums.ReservationStatus;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchBikeException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchCyclistException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchReservationException;
import com.mmokijewski.bikeRentalApp.mapper.ReservationMapper;
import com.mmokijewski.bikeRentalApp.repository.BikeRepository;
import com.mmokijewski.bikeRentalApp.repository.CyclistRepository;
import com.mmokijewski.bikeRentalApp.repository.ReservationRepository;
import com.mmokijewski.bikeRentalApp.service.ReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public List<ReservationDto> getAllReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        LOGGER.info("Found: {} reservation(s)", reservations.size());
        return reservationMapper.mapToDtos(reservations);
    }

    @Override
    public ReservationDto findById(final Long id) {
        final Optional<Reservation> reservation = this.reservationRepository.findById(id);
        return reservation.map(this.reservationMapper::mapToDto).orElse(null);
    }

    @Override
    public ReservationDto createReservation(final Long bikeId, final Long cyclistId)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        return createReservation(bikeId, cyclistId, DEFAULT_RESERVATION_TIME_IN_MINUTES);
    }

    @Override
    public ReservationDto createReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        final ReservationDto reservation = createNewReservation(bikeId, cyclistId, minutes);
        finishReservationInFuture(reservation.getId(), minutes);
        return reservation;
    }

    @Override
    public ReservationDto cancelReservation(final Long id) throws NoSuchReservationException {
        final Reservation reservation =
                reservationRepository.findById(id).orElseThrow(() -> new NoSuchReservationException(id));
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        final Reservation saveResult = reservationRepository.saveAndFlush(reservation);
        return reservationMapper.mapToDto(saveResult);
    }

    private ReservationDto createNewReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        final Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new NoSuchBikeException(bikeId));
        final Cyclist cyclist =
                cyclistRepository.findById(cyclistId).orElseThrow(() -> new NoSuchCyclistException(cyclistId));
        if (checkIfBikeAvailable(bike)) {
            final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            final LocalDateTime end = now.plus(minutes, ChronoUnit.MINUTES);
            final Reservation newReservation = new Reservation(bike, cyclist, now, end);
            final Reservation saveResult = reservationRepository.saveAndFlush(newReservation);
            return reservationMapper.mapToDto(saveResult);
        } else {
            throw new BikeNotAvailableException(bike.getId());
        }
    }

    private boolean checkIfBikeAvailable(final Bike bike) {
        final Reservation lastReservation;
        try {
            lastReservation = reservationRepository.findLastReservationByBikeId(bike.getId());
        } catch (final EmptyResultDataAccessException e) {
            LOGGER.info("There are no reservations for bike with id '{}' yet", bike.getId());
            return true;
        }
        return lastReservation.getEndDate().isBefore(LocalDateTime.now());
    }

    private void finishReservationInFuture(final Long id, final int minutes) {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        final Runnable task = () -> {
            final Optional<Reservation> reservationOptional = reservationRepository.findById(id);
            if (reservationOptional.isPresent()) {
                final Reservation reservation = reservationOptional.get();
                reservation.setStatus(ReservationStatus.FINISHED);
                reservation.setUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                reservationRepository.saveAndFlush(reservation);
            }
        };
        executorService.schedule(task, minutes, TimeUnit.SECONDS);
    }
}