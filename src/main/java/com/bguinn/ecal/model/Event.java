package com.bguinn.ecal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String eventName;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public Event() {
        super();
    }

    private Event(EventBuilder builder) {
        this.eventName = builder.eventName;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public static class EventBuilder {
        private String eventName;
        private LocalDate startDate;
        private LocalDate endDate;

        public EventBuilder(String eventName) {
            this.eventName = eventName;
        }

        public EventBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public EventBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        //Return the finally constructed Event  object
        public Event build() {
            Event event = new Event(this);
            validateEventObject(event);
            return event;
        }

        private void validateEventObject(Event event) {
            // Do some basic validations to check - to do
        }

    }


}
