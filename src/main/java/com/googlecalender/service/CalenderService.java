package com.googlecalender.service;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.googlecalender.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalenderService implements ICalenderService{


    @Value("${{google.application_name}")
    private String applicationName;

    private static final Logger log = LoggerFactory.getLogger(CalenderService.class);
    private Calendar getCalenderClient(Credential credential) throws IOException, GeneralSecurityException {
        try{
            log.info("The application name is " + applicationName);
            return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport() ,
                    GsonFactory.getDefaultInstance(),
                    credential).setApplicationName(applicationName).build();
        }catch(IOException exception){
            throw exception;
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String createEvent(EventDTO eventDTO, String accessToken, Long expireSecondsTime) {
        try{
            Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
            credential.setAccessToken(accessToken);
            credential.setExpiresInSeconds(expireSecondsTime);

            Calendar client = getCalenderClient(credential);

            Event event = new Event();
            event.setSummary(eventDTO.getEventTitle());
            event.setDescription(eventDTO.getEventDescription());
            String startString = eventDTO.getEventStartDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            EventDateTime eventStartDate = new EventDateTime();
            eventStartDate.setDateTime(DateTime.parseRfc3339(startString));
            event.setStart(eventStartDate);

            String endDate= eventDTO.getEventEndDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            EventDateTime eventEndDate = new EventDateTime();
            eventEndDate.setDateTime(DateTime.parseRfc3339(endDate));
            event.setEnd(eventEndDate);

           List<EventAttendee> eventAttendeeList = new ArrayList<>();
           for(String attendEmail : eventDTO.getInviteUserEmailIds()){
               eventAttendeeList.add(new EventAttendee().setEmail(attendEmail));
           }
           event.setAttendees(eventAttendeeList);

           String calenderId = eventDTO.getCalendarId();

           event = client.events().insert(calenderId, event).setSendNotifications(true).execute();
           if(event !=null){
               log.info("Calender is created -> cal creator email : {}, cal id : {}, cal Uid : {}", event.getCreator().getEmail(),event.getId(), event.getICalUID());
              return String.format("SUCCESS \n create html : %s",event.getHtmlLink());
           }
        return "Create Failed";
        }catch (IOException exception){
            log.error("An IOException thrown : ",exception);
        }
        catch (Exception e) {
            log.error("An exception thrown : ",e);
        }
        return "";
    }

    @Override
    public List<Event> getAllEvents(String accessToken, Long expireSecondsTime, LocalDateTime startTime, LocalDateTime endTime) {
        try{
            Credential credential= new Credential(BearerToken.authorizationHeaderAccessMethod());
            credential.setAccessToken(accessToken);
            credential.setExpiresInSeconds(expireSecondsTime);
            Calendar client = getCalenderClient(credential);
           // DateTime stDate=
            DateTime startDate= DateTime.parseRfc3339(startTime.toString());
            DateTime endDate= DateTime.parseRfc3339(endTime.toString());
           // DateTime now = new DateTime(System.currentTimeMillis());
            Events event = client.events().list("primary")
                    .setTimeMin(startDate).
                   setTimeMax(endDate).
                    setOrderBy("startTime").
                    setSingleEvents(true).execute();

            log.info("Event items : {}", event.getItems().size());
            return event.getItems();
        }catch (Exception e){
            log.error("An exception thrown : ",e);
            return List.of();
        }
    }

    @Override
    public List<CalendarListEntry> getCalendarList(String accessToken, Long expireSecondsTime) {
        try{
            Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
            credential.setAccessToken(accessToken);
            credential.setExpiresInSeconds(expireSecondsTime);

            Calendar client = getCalenderClient(credential);
            CalendarList calendarList= client.calendarList().list().execute();
            return calendarList.getItems();
        } catch (Exception e) {
            log.error("An exception thrown : ",e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> getAllCalendarEventByCalendarId(String accessToken, Long expireSecondsTime, String calendarId) {
        try{
            Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
            credential.setAccessToken(accessToken);
            credential.setExpiresInSeconds(expireSecondsTime);
            Calendar client = getCalenderClient(credential);
            Events calendarList= client.events().list(calendarId).setOrderBy("startTime").setSingleEvents(true).execute();
            return calendarList.getItems();
        }catch (Exception e){
            log.error("An exception thrown : ",e);
        }
        return List.of();
    }
}
