package com.googlecalender.controller;


import com.google.api.services.calendar.model.Event;
import com.googlecalender.dto.EventDTO;
import com.googlecalender.service.ICalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/calender")
public class GoogleCalenderController {

    @Autowired
    private ICalenderService calenderService;

    @PostMapping("/create")
    public ResponseEntity<String> createEvente(@RequestBody EventDTO eventDTO, @RequestParam String credintialId,@RequestParam Long expireTimeSpan) {
        String resposen = this.calenderService.createEvent(eventDTO, credintialId, expireTimeSpan);
       return new ResponseEntity<>(resposen, HttpStatus.CREATED);
    }

    @GetMapping("/user-all-events")
    public ResponseEntity<List<Event>> getAllEventsByUser(@RequestParam String credentialId, @RequestParam Long expireTimeSpan,
                                                          @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        List<Event> userEventList = this.calenderService.getAllEvents(credentialId, expireTimeSpan, startTime, endTime);
        return new ResponseEntity<>(userEventList, HttpStatus.OK);
    }
}
