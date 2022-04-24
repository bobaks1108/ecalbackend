package com.bguinn.ecal.service;

import java.util.List;

import com.bguinn.ecal.model.Event;

public interface EventService {
	
	Event saveEvent(Event event);
	List<Event> getAllEvents();
	Event getEventById(long id);
	Event updateEvent(Event event, long id);
	void deleteEvent(long id);

}
