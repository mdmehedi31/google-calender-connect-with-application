package com.googlecalender.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.calendar.Calendar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CalenderService implements ICalenderService{


    @Value("${{google.application.name}")
    private String applicationName;


    private Calendar getCalenderClient(String googleCalendarToken){

        return null;
    }


}
