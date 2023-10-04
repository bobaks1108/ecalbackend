package com.bguinn.ecal.service;

import com.bguinn.ecal.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    Event saveEvent(Event event);

    List<Event> getAllEvents();

    Event getEventById(long id);

    Event updateEvent(Event event, long id);

    void deleteEvent(long id);

    List<Event> findAllWithStartDateTimeOnOrBefore(LocalDate parse);

    void deleteAll();

    List<Event> findByName(String string);

}
