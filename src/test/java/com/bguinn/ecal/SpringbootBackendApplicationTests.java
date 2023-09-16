package com.bguinn.ecal;

import com.bguinn.ecal.model.Event;
import com.bguinn.ecal.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static com.bguinn.ecal.TestUtility.initialiseDatabase;
import static com.bguinn.ecal.TestUtility.removeDB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterAll
    static void cleanUp() {
        System.out.println("Remove in memory test database.");
        removeDB("eventsTestDB");
    }

    /************************
     *
     *  Setup before tests
     *
     ************************/

    private ResultActions getResponseFromAddingEvent(Event event) throws Exception {

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)));

        return response;
    }

    @BeforeEach
    void setUp() {
        System.out.println("Clear any events in the events from previous tests in the events repository.");
        eventService.deleteAll();
    }


    // ************************
    // **
    // **  Tests
    // **
    // ************************


    // ***************************
    // **
    // **  Test is event created
    // **
    // **************************

    @Test
    public void test001_eventIsCreatedTest() throws Exception {

        initialiseDatabase();

        Event event = createEventPlusDays("Test Event Created", 7);

        ResultActions response1 = getResponseFromAddingEvent(event);

        List<Event> result1 = eventService.findByName("Test Event Created");

        assertEquals(1, result1.size());

        assertTrue(result1.stream()
                .map(Event::getEventName)
                .allMatch(eventName -> Arrays.asList("Test Event Created").contains(eventName)));


    }

    /**************************
     *  Test - When test events are added
     *  the correct event(s) is returned
     *  by the service findAllWithStartDateTimeOnOrBefore
     *  including not returning events in the past
     *  Here after adding events 7 days ago, in 7 days time and 48 days time
     *  Then the findAllWithStartDateTimeOnOrBefore date is set to 28 days in the future
     *  So it should only find event the event in 7 days time.
     **************************/


    @Test
    public void test002_givenEventsWhenFindWithStartDateThenRightReturned() throws Exception {

        initialiseDatabase();

        Event event1 = createEventPlusDays("Test Event - find using start date - plus 7 Days", 7);
        Event event2 = createEventPlusDays("Test Event - find using start date - plus 48 Days", 48);
        Event event3 = createEventPlusDays("Test Event - find using start date - minus 7 Days", -7);

        int daysInToFutureForDateToCheckBefore = 28;

        ResultActions response1 = getResponseFromAddingEvent(event1);
        ResultActions response2 = getResponseFromAddingEvent(event2);
        ResultActions response3 = getResponseFromAddingEvent(event3);

        OffsetDateTime now = OffsetDateTime.now();

        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

        OffsetDateTime currentDatePlusDays = now.plusHours(daysInToFutureForDateToCheckBefore * 24);


        List<Event> result = eventService.findAllWithStartDateTimeOnOrBefore(
                currentDatePlusDays);

        assertEquals(1, result.size());

        assertTrue(result.stream()
                .map(Event::getEventName)
                .allMatch(eventName -> Arrays.asList("Test Event - find using start date - plus 7 Days").contains(eventName)));

    }

    // ***************************
    // **
    // **  Test - When test events are added
    // **  the correct event(s) is returned
    // **  by the service findByName
    // **
    // **************************

    @Test
    public void test003_givenEventsWhenFindWithNameFindsEvents() throws Exception {

        Event event1 = createEventPlusDays("Test Event - find with name - plus 7 days", 7);
        Event event2 = createEventPlusDays("Test Event - find with name - plus 7 weeks", 49);
        Event event3 = createEventPlusDays("Test Event - find with name - minus 1 week", -7);

        int daysInToFutureForDateToCheckBefore = 28;

        ResultActions response1 = getResponseFromAddingEvent(event1);
        ResultActions response2 = getResponseFromAddingEvent(event2);
        ResultActions response3 = getResponseFromAddingEvent(event3);

        List<Event> result1 = eventService.findByName("Test Event - find with name - plus 7 weeks");

        assertEquals(1, result1.size());

        assertTrue(result1.stream()
                .map(Event::getEventName)
                .allMatch(eventName -> Arrays.asList("Test Event - find with name - plus 7 weeks").contains(eventName)));

    }

    /************************
     *
     *  Methods for tests
     *
     ************************/


    private Event createEventPlusDays(String eventName, int noOfDaysToAddOrMinus) {

        OffsetDateTime now = OffsetDateTime.now();

        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

        OffsetDateTime currentDatePlusOrMinusDays = now.plusHours(noOfDaysToAddOrMinus * 24);

        Event event = new Event.EventBuilder(eventName)
                .startDate(currentDatePlusOrMinusDays)
                .endDate(currentDatePlusOrMinusDays)
                .build();

        return event;

    }


}
	
	



