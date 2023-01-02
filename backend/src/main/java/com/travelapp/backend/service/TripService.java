package com.travelapp.backend.service;

import com.travelapp.backend.entity.Trip;
import com.travelapp.backend.repository.TripRepository;
import com.travelapp.backend.responses.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    @Transactional
    public ResponseObject addTrip(Trip trip) {
        tripRepository.save(trip);
        return new ResponseObject(HttpStatus.CREATED.value(), "xD");
    }
}
