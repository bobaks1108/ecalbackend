package com.bguinn.ecal;

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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

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
    
   private ZonedDateTime getDatePlusMinusDays(int noOfDaysToAddOrMinus) {
	   
       ZonedDateTime now = ZonedDateTime.now();

       return now.plusHours(noOfDaysToAddOrMinus*24);
   
   }

    @BeforeEach
    void setup(){
    	eventService.deleteAll();
    }

	@Test
	void contextLoads() {
	}
	
	@Test
    public void givenEventsWhenFindWithStartDateThenEvents2And3Returned() throws Exception {
		
		
		// always use UTC in the db
//		// change to local time to show user in the UI
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		// ZZZZ is the time zone   
        // +0000 means UTC
		
        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
        
        String date1str = "2022-05-02T00:00:00+00:00";
        String date2str = "2022-05-20T00:00:00+00:00";
        String date3str = "2022-12-03T00:00:00+00:00";
        
        // create an event in the past 7 days ago
        // create an event in 7 days
        // create an event in 48 days
        // test is for events in next 28 days
       
        
        // String to LocalDateTime
        LocalDateTime date1ldt = LocalDateTime.parse(date1str, DateTimeFormatter.ofPattern(dateTimeFormat));
        // Set the generated LocalDateTime's TimeZone. In this case I set it to UTC
        ZonedDateTime date1 = date1ldt.atZone(ZoneOffset.UTC);
        	
        LocalDateTime date2ldt = LocalDateTime.parse(date2str, DateTimeFormatter.ofPattern(dateTimeFormat));
        // Set the generated LocalDateTime's TimeZone. In this case I set it to UTC
        ZonedDateTime date2 = date2ldt.atZone(ZoneOffset.UTC);
        
        LocalDateTime date3ldt = LocalDateTime.parse(date3str, DateTimeFormatter.ofPattern(dateTimeFormat));
        // Set the generated LocalDateTime's TimeZone. In this case I set it to UTC
        ZonedDateTime date3 = date3ldt.atZone(ZoneOffset.UTC);
		
		
		// example with milliseconds
		// ... new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ");
		// eg prints: 2017-02-19T22:37:38.634-0800
		
		
		// given - precondition or setup
        Event event1 = new Event.EventBuilder("event1 "+date1.toString())
                .startDate(date1)
                .endDate(date1)
                .build();
        
        Event event2 = new Event.EventBuilder("event2 "+date2.toString())
                .startDate(date2)
                .endDate(date2)
                .build();
        
        Event event3 = new Event.EventBuilder("event3  "+date3.toString())
                .startDate(date3)
                .endDate(date3)
                .build();
        
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event1)));
        
        // when - action or behaviour that we are going test
        ResultActions response1 = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event2)));
        
        // when - action or behaviour that we are going test
        ResultActions response2 = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event3)));
       

        
        // how tests work and how to read failure
        // for .andExpect test failures e.g
        
        // with getEndDate().toString()
		//        java.lang.AssertionError: JSON path "$.startDate"
		//        Expected: is "Wed Apr 06 00:00:00 UTC 2022"
		//             but: was "2022-04-06T00:00:00Z"
        
        // 'Expected:is "xxxxx"' the 1st value in the failure,
        //  is the second value in the code the 'is'.
        
        //  but was "yyyyy" the 2nd value in the failure,
        //  is the first value in the code

        // with getEndDate().toString()
		//        java.lang.AssertionError: JSON path "$.startDate"
		//        Expected: is "Wed Apr 06 00:00:00 UTC 2022"
		//             but: was "2022-04-06T00:00:00Z"
        
        // with getEndDate().toInstant()
		//        java.lang.AssertionError: JSON path "$.startDate"
		//        Expected: is <2022-04-06T00:00:00Z>
		//             but: was "2022-04-06T00:00:00Z"
        
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(event1.getName())))
                .andExpect(jsonPath("$.startDate",
                        is(event1.getStartDate().toInstant().toString())))
                .andExpect(jsonPath("$.endDate",
                        is(event1.getEndDate().toInstant().toString())));
		
		
        List<Event> result = eventService.findAllWithStartDateTimeOnOrBefore(
        		ZonedDateTime.parse("2022-05-30T00:00:00+00:00"));

        assertEquals(1, result.size());
        
        assertTrue(result.stream()
          .map(Event::getName)
          .allMatch(name -> Arrays.asList("event2 "+date2.toString()).contains(name.toString())));
        
    }

//	@Test
//	public void givenEventsWhenFindWithNameFindsEvents() throws Exception {
//		
//		
//		// always use UTC in the db
//		// change to local time to show user in the UI
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//		
//		// ZZZZ is the time zone 
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
//	    
//	    // +0000 means UTC
//                Date date1 = sdf.parse("2022-04-20T00:00:00+0000");
//		
//		Date date2 = sdf.parse("2022-05-25T00:00:00+0000");
//		
//		Date date3 = sdf.parse("2022-12-01T00:00:00+0000");
//		
//		
//		// example with milliseconds
//		// ... new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ");
//		// eg prints: 2017-02-19T22:37:38.634-0800
//		
//		
//		// given - precondition or setup
//        Event event1 = new Event.EventBuilder("event1 "+date1.toString())
//                .startDate(date1)
//                .endDate(date1)
//                .build();
//        
//        Event event2 = new Event.EventBuilder("event2 "+date2.toString())
//                .startDate(date2)
//                .endDate(date2)
//                .build();
//        
//        Event event3 = new Event.EventBuilder("event3  "+date3.toString())
//                .startDate(date3)
//                .endDate(date3)
//                .build();
//        
//	    
//	    // when - action or behaviour that we are going test
//	    ResultActions response = mockMvc.perform(post("/api/events")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(event1)));
//	    
//	    // when - action or behaviour that we are going test
//	    ResultActions response1 = mockMvc.perform(post("/api/events")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(event2)));
//	    
//	    // when - action or behaviour that we are going test
//	    ResultActions response2 = mockMvc.perform(post("/api/events")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(event3)));
//	   
//	
//	    
//	    response.andDo(print()).
//	            andExpect(status().isCreated())
//	            .andExpect(jsonPath("$.name",
//	                    is(event1.getName())))
//	            .andExpect(jsonPath("$.startDate",
//	                    is(event1.getStartDate().toInstant().toString())))
//	            .andExpect(jsonPath("$.endDate",
//	                    is(event1.getEndDate().toInstant().toString())));
//		
//	    
//	    List<Event> result1 = eventService.findByName("event2");
//	    
//	    assertEquals(1, result1.size());
//	    
//	    assertTrue(result1.stream()
//	            .map(Event::getName)
//	            .allMatch(name -> Arrays.asList("event2 "+date2.toString()).contains(name.toString())));
//	    
//		}

}
