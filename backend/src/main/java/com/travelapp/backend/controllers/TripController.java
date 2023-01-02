package com.travelapp.backend.controllers;

import com.travelapp.backend.entity.Trip;
import com.travelapp.backend.responses.ResponseObject;
import com.travelapp.backend.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/add")
    public ResponseObject addTrip(@RequestBody Trip trip){
        return tripService.addTrip(trip);
    }
}
