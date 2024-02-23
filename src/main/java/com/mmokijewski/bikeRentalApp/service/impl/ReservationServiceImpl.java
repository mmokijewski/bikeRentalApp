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
import com.mmokijewski.bikeRentalApp.enums.BikeStatus;
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
    public ReservationDto findLastReservation() {
        final Optional<Reservation> lastReservation = this.reservationRepository.findLastReservation();
        return lastReservation.map(this.reservationMapper::mapToDto).orElse(null);
    }

    @Override
    public ReservationDto findLastReservationByBikeId(Long bikeId) throws NoSuchBikeException {
        final Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new NoSuchBikeException(bikeId));
        final Optional<Reservation> lastReservation = this.reservationRepository.findLastReservationByBikeId(bike.getId());
        return lastReservation.map(this.reservationMapper::mapToDto).orElse(null);
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
        releaseBike(reservation.getBike().getId());
        return reservationMapper.mapToDto(saveResult);
    }

    private ReservationDto createNewReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException {
        final Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new NoSuchBikeException(bikeId));
        final Cyclist cyclist =
                cyclistRepository.findById(cyclistId).orElseThrow(() -> new NoSuchCyclistException(cyclistId));
        if (bike.getStatus().equals(BikeStatus.FREE)) {
            final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            final LocalDateTime end = now.plus(minutes, ChronoUnit.MINUTES);
            final Reservation newReservation = new Reservation(bike, cyclist, now, end);
            final Reservation saveResult = reservationRepository.saveAndFlush(newReservation);
            bike.setStatus(BikeStatus.RESERVED);
            bikeRepository.saveAndFlush(bike);
            return reservationMapper.mapToDto(saveResult);
        } else {
            throw new BikeNotAvailableException(bike.getId());
        }
    }

    private void finishReservationInFuture(final Long reservationId, final int minutes) {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        final Runnable task = () -> {
            final Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
            if (reservationOptional.isPresent()) {
                final Reservation reservation = reservationOptional.get();
                reservation.setStatus(ReservationStatus.FINISHED);
                reservation.setUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                reservationRepository.saveAndFlush(reservation);
                releaseBike(reservation.getBike().getId());
            }
        };
        executorService.schedule(task, minutes, TimeUnit.MINUTES);
    }

    private void releaseBike(final Long bikeId) {
        final Bike bike = bikeRepository.findById(bikeId).get();
        bike.setStatus(BikeStatus.FREE);
        bikeRepository.saveAndFlush(bike);
    }
}