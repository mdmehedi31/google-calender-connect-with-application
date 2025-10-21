package com.googlecalender.service;

import com.googlecalender.dto.EventDTO;

public interface ICalenderService {

    public String createEvent(EventDTO eventDTO, String accessToken, Long expireSecondsTime);

}
