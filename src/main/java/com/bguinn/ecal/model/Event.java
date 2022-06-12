package com.bguinn.ecal.model;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

//2:22 Spring Boot Youtube Tutorial

@Data
@Entity
@Table(name="events")
public class Event {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String eventName;
	
	@Column(name="start_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssZZZZZ")
	private ZonedDateTime startDate;
	
	@Column(name="end_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssZZZZZ")
	private ZonedDateTime endDate;
	
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

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}


	public static class EventBuilder
	{
		private String eventName;
		private ZonedDateTime startDate;
		private ZonedDateTime endDate;

		public EventBuilder(String eventName) {
			this.eventName = eventName;
		}
		
		public EventBuilder startDate(ZonedDateTime startDate) {
			this.startDate = startDate;
			return this;
		}
		
		public EventBuilder endDate(ZonedDateTime endDate) {
			this.endDate = endDate;
			return this;
		}

		//Return the finally constructed Event  object
		public Event build() {
			Event event =  new Event(this);
			validateEventObject(event);
			return event;
		} 
		
		private void validateEventObject(Event event) {
			//Do some basic validations to check
			//if user object does not break any assumption of system
		}

	}
	

}
