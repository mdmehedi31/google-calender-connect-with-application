package com.googlecalender.service;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.googlecalender.dto.EventDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface ICalenderService {

    public String createEvent(EventDTO eventDTO, String accessToken, Long expireSecondsTime);
    public List<Event> getAllEvents(String accessToken, Long expireSecondsTime, LocalDateTime startTime, LocalDateTime endTime);
    public List<CalendarListEntry> getCalendarList(String accessToken, Long expireSecondsTime);
}
