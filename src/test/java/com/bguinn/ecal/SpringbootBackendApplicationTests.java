package com.bguinn.ecal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bguinn.ecal.model.Event;
import com.bguinn.ecal.repository.EventRepository;
import com.bguinn.ecal.service.EventService;

@SpringBootTest
class SpringbootBackendApplicationTests {
	
    @Autowired
	private EventService eventService;

	@Test
	void contextLoads() {
	}
	
	@Test
    public void givenEventsWhenFindWithStartDateThenEvents2And3Returned() throws Exception {
        List<Event> result = eventService.findAllWithStartDateTimeBefore(
          new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2022-04-07 10:00"));

        assertEquals(2, result.size());
        assertTrue(result.stream()
          .map(Event::getId)
          .allMatch(id -> Arrays.asList("35", "12").contains(id.toString())));
    }

}
