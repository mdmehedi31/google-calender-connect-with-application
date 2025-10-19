package com.googlecalender.dto;

import java.time.Instant;
import java.util.List;

public class EventDTO {

    private String eventTitle;
    private String eventDescription;
    private Instant eventStartDateTime;
    private Instant eventEndDateTime;
    private String calendarId;
    private List<String> inviteUserEmailIds;
    private String credentialId;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Instant getEventStartDateTime() {
        return eventStartDateTime;
    }

    public void setEventStartDateTime(Instant eventStartDateTime) {
        this.eventStartDateTime = eventStartDateTime;
    }

    public Instant getEventEndDateTime() {
        return eventEndDateTime;
    }

    public void setEventEndDateTime(Instant eventEndDateTime) {
        this.eventEndDateTime = eventEndDateTime;
    }

    public List<String> getInviteUserEmailIds() {
        return inviteUserEmailIds;
    }

    public void setInviteUserEmailIds(List<String> inviteUserEmailIds) {
        this.inviteUserEmailIds = inviteUserEmailIds;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }
}
