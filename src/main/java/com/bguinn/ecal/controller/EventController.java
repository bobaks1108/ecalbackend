package com.bguinn.ecal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bguinn.ecal.model.Event;
import com.bguinn.ecal.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {
	
	private EventService eventService;

	public EventController(EventService eventService) {
		super();
		this.eventService = eventService;
	}
	

    // build create event REST API
	@PostMapping()
	public ResponseEntity<Event> saveEvent(@RequestBody Event event) {
		return new ResponseEntity<Event>(eventService.saveEvent(event), HttpStatus.CREATED);	
	}
	
	// build get all events REST API
	@GetMapping
	public List<Event> getAllEvents() {
		return eventService.getAllEvents();
	};
	
	// build get event by id REST API
	// http://localhost:8080/api/events/1
	@GetMapping("{id}")
	public ResponseEntity<Event> getEventById(@PathVariable("id") long id) {
		return new ResponseEntity<Event>(eventService.getEventById(id), HttpStatus.OK);
	}
	
	// Build update event Rest api
	@PutMapping("{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable("id") long id
											,@RequestBody Event event) {
		return new ResponseEntity<Event>(eventService.updateEvent(event, id), HttpStatus.OK);
	
	}
	
	
	// From https://www.sourcecodeexamples.net/2019/10/deletemapping-spring-boot-example.html
	// Build delete event Rest api
	@DeleteMapping("{id}")
	public Map<String, Boolean> deleteEvent(@PathVariable("id") long id) {
		
		eventService.deleteEvent(id);
		
	     Map<String, Boolean> response = new HashMap<>();
	     response.put("deleted", Boolean.TRUE);
	     return response;
	}
	
	
//	Original below caused 
//  EventService: deleteEvent failed: Http failure during parsing for http://localhost:4200/server/api/events/38
//	// build delete employee REST API
//	// http://localhost:8080/api/employees/1
//	@DeleteMapping("{id}")
//	public ResponseEntity<String> deleteEvent(@PathVariable("id") long id){
//		
//		// delete employee from DB
//		eventService.deleteEvent(id);
//		
//		return new ResponseEntity<String>("Event deleted successfully!.", HttpStatus.OK);
//	}
	

}
