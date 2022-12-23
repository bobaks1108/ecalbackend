package com.bguinn.ecal.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import com.bguinn.ecal.model.Event;

public interface EventService {
	
	Event saveEvent(Event event);
	List<Event> getAllEvents();
	Event getEventById(long id);
	Event updateEvent(Event event, long id);
	void deleteEvent(long id);
	List<Event> findAllWithStartDateTimeOnOrBefore(OffsetDateTime parse);
	void deleteAll();
	List<Event> findByName(String string);

}
