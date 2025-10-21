package com.googlecalender.controller;


import com.googlecalender.dto.EventDTO;
import com.googlecalender.service.ICalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
