package com.bguinn.ecal.service.impl;

import com.bguinn.ecal.exception.ResourceNotFoundException;
import com.bguinn.ecal.model.Event;
import com.bguinn.ecal.repository.EventRepository;
import com.bguinn.ecal.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        super();
        this.eventRepository = eventRepository;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new ResourceNotFoundException("Event", "id", id);
        }
    }

    @Override
    public Event updateEvent(Event event, long id) {

        // check whether event exists
        Event existingEvent = eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Event", "Id", id));

        existingEvent.setEventName(event.getEventName());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());

        // save existing Event
        eventRepository.save(existingEvent);

        return existingEvent;
    }

    @Override
    public void deleteEvent(long id) {

        // check if event exist
        eventRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("event", "Id", id));

        eventRepository.deleteById(id);

    }

    @Override
    public void deleteAll() {

        eventRepository.deleteAll();

    }


    @Override
    public List<Event> findAllWithStartDateTimeOnOrBefore(LocalDate parse) {
        return eventRepository.findAllWithStartDateTimeOnOrBefore(parse);
    }

    @Override
    public List<Event> findByName(String filterText) {
        return eventRepository.findByName(filterText);
    }


}
