package com.bguinn.ecal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bguinn.ecal.model.Event;
import com.bguinn.ecal.service.EventService;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootBackendApplicationTests {
	
    @Autowired
    private MockMvc mockMvc;
	
    @Autowired
	private EventService eventService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // ************************
    // **
    // **  Methods for tests
    // **
    // ************************
    
    private Event createEventPlusDays(String eventName, int noOfDaysToAddOrMinus) {
	   
	  OffsetDateTime now = OffsetDateTime.now();
	   
       String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

		OffsetDateTime currentDatePlusOrMinusDays = now.plusHours(noOfDaysToAddOrMinus*24);

		//OffsetDateTime currentDatePlusOrMinusDaysLdt = OffsetDateTime.parse(currentDatePlusOrMinusDays.truncatedTo(ChronoUnit.SECONDS).toString(), DateTimeFormatter.ofPattern(dateTimeFormat));
       
       //OffsetDateTime dateZdt = currentDatePlusOrMinusDaysLdt.atZone(ZoneOffset.UTC);
       
       Event event = new Event.EventBuilder(eventName)
               .startDate(currentDatePlusOrMinusDays)
               .endDate(currentDatePlusOrMinusDays)
               .build();

       return event;
   
   }
   
   private ResultActions getResponseFromAddingEvent(Event event) throws JsonProcessingException, Exception {
   
	   // when - action or behaviour that we are going test
	   ResultActions response = mockMvc.perform(post("/api/events")
	           .contentType(MediaType.APPLICATION_JSON)
	           .content(objectMapper.writeValueAsString(event)));
	   
	   return response;
   }
   
   // ************************
   // **
   // **  Setup before tests
   // **
   // ************************

    @BeforeEach
    void setup(){
    	eventService.deleteAll();
    }
    
    @AfterAll
    public static void cleanUp(){
    	System.out.println("After All cleanUp() method called");
    	
    }
    
    
    // ************************
    // **
    // **  Tests
    // **
    // ************************

	@Test
	void contextLoads() {
	}
	
    // ***************************
    // **
    // **  Test is event created
    // **
    // **************************
	
	@Test
    public void eventIsCreatedTest() throws Exception {
		
		Event event = createEventPlusDays("Event1", 7);
		
		ResultActions response = getResponseFromAddingEvent(event);
		
        response.andDo(print()).
        andExpect(status().isCreated())
        .andExpect(jsonPath("$.eventName",
                is(event.getEventName())))
        .andExpect(jsonPath("$.startDate",
                is(event.getStartDate().truncatedTo(ChronoUnit.SECONDS).toString())))
        .andExpect(jsonPath("$.endDate",
                is(event.getEndDate().truncatedTo(ChronoUnit.SECONDS).toString())));

	}
	
    // ***************************
    // **
    // **  Test - When test events are added
	// **  the correct event(s) is returned
	// **  by the service findAllWithStartDateTimeOnOrBefore
	// **  including not returning events in the past
	// **  Here after adding events 7 days ago, in 7 days time and 48 days time
	// **  Then the findAllWithStartDateTimeOnOrBefore date is set to 28 days in the future
	// **  So it should only find event the event in 7 days time. 
    // **
    // **************************
	

	@SuppressWarnings("unused")
	@Test
    public void givenEventsWhenFindWithStartDateThenRightReturned() throws Exception {
		
		Event event1 = createEventPlusDays("Event1", 7);
		Event event2 = createEventPlusDays("Event2", 48);
		Event event3 = createEventPlusDays("Event3", -7);
		
		int daysInToFutureForDateToCheckBefore = 28;
		
		ResultActions response1 = getResponseFromAddingEvent(event1);
		ResultActions response2 = getResponseFromAddingEvent(event2);
		ResultActions response3 = getResponseFromAddingEvent(event3);
		
	   OffsetDateTime now = OffsetDateTime.now();
	   
       String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

		OffsetDateTime currentDatePlusDays = now.plusHours(daysInToFutureForDateToCheckBefore*24);

		//OffsetDateTime currentDatePlusDaysLdt = OffsetDateTime.parse(currentDatePlusDays.truncatedTo(ChronoUnit.SECONDS).toString(), DateTimeFormatter.ofPattern(dateTimeFormat));
       
       //OffsetDateTime dateZdt = currentDatePlusDaysLdt.atZone(ZoneOffset.UTC);
        
        List<Event> result = eventService.findAllWithStartDateTimeOnOrBefore(
				currentDatePlusDays);

        assertEquals(1, result.size());
        
        assertTrue(result.stream()
          .map(Event::getEventName)
          .allMatch(eventName -> Arrays.asList("Event1").contains(eventName.toString())));

	}
	
    // ***************************
    // **
    // **  Test - When test events are added
	// **  the correct event(s) is returned
	// **  by the service findByName
    // **
    // **************************
	
	@SuppressWarnings("unused")
	@Test
    public void givenEventsWhenFindWithNameFindsEvents() throws Exception {
		
		Event event1 = createEventPlusDays("Event1", 7);
		Event event2 = createEventPlusDays("Event2", 48);
		Event event3 = createEventPlusDays("Event3", -7);
		
		int daysInToFutureForDateToCheckBefore = 28;
		
		ResultActions response1 = getResponseFromAddingEvent(event1);
		ResultActions response2 = getResponseFromAddingEvent(event2);
		ResultActions response3 = getResponseFromAddingEvent(event3);
        
		List<Event> result1 = eventService.findByName("event2");
		
		assertEquals(1, result1.size());
		
		assertTrue(result1.stream()
		        .map(Event::getEventName)
		        .allMatch(eventName -> Arrays.asList("Event2").contains(eventName.toString())));
		
	}

}
	
	



