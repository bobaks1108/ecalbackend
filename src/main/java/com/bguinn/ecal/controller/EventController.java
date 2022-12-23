package com.bguinn.ecal.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bguinn.ecal.model.Event;
import com.bguinn.ecal.service.EventService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://ecal-frontend.s3-website.eu-west-2.amazonaws.com"})
@RequestMapping("/api/events")
public class EventController {
	
	private EventService eventService;

	public EventController(EventService eventService) {
		super();
		this.eventService = eventService;
	}
	

    // create event REST API
	@PostMapping()
	public ResponseEntity<Event> saveEvent(@RequestBody Event event) {
		return new ResponseEntity<Event>(eventService.saveEvent(event), HttpStatus.CREATED);	
	}
	
	// get all events REST API
	@GetMapping
	public List<Event> getAllEvents() {
		return eventService.getAllEvents();
	};
	
	// get event by id REST API
	@GetMapping("{id}")
	public ResponseEntity<Event> getEventById(@PathVariable("id") long id) {
		return new ResponseEntity<Event>(eventService.getEventById(id), HttpStatus.OK);
	}
	
	// update event Rest api
	@PutMapping("{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable("id") long id
											,@RequestBody Event event) {
		return new ResponseEntity<Event>(eventService.updateEvent(event, id), HttpStatus.OK);
	
	}
	
	// delete event Rest api
	@DeleteMapping("{id}")
	public Map<String, Boolean> deleteEvent(@PathVariable("id") long id) {
		
		eventService.deleteEvent(id);
		
	     Map<String, Boolean> response = new HashMap<>();
	     response.put("deleted", Boolean.TRUE);
	     return response;
	}
	

	@GetMapping("/search")
	public List<Event> findByName(@RequestParam(value = "eventName", defaultValue = "") String eventName) {
		return eventService.findByName(eventName);
	}
	
	// find all events with start date on or before
	// build get all events REST API
	@GetMapping("/upcoming")
	public List<Event> findAllInTheNextNoOfDays(@RequestParam(value = "noOfDays", defaultValue = "0") String noOfDays) {
		
		int noOfDaysInt=0;
		
        try{
            noOfDaysInt = Integer.parseInt(noOfDays);
            System.out.println(noOfDays); // output = 25
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }

        OffsetDateTime now = OffsetDateTime.now();

		OffsetDateTime currentDatePlus = now.plusHours(noOfDaysInt*24);
	
		return eventService.findAllWithStartDateTimeOnOrBefore(currentDatePlus);
	};
		
}
