package com.bguinn.ecal.model;

import java.time.OffsetDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

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
	private OffsetDateTime startDate;
	
	@Column(name="end_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssZZZZZ")
	private OffsetDateTime endDate;
	
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

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	public OffsetDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}


	public static class EventBuilder
	{
		private String eventName;
		private OffsetDateTime startDate;
		private OffsetDateTime endDate;

		public EventBuilder(String eventName) {
			this.eventName = eventName;
		}
		
		public EventBuilder startDate(OffsetDateTime startDate) {
			this.startDate = startDate;
			return this;
		}
		
		public EventBuilder endDate(OffsetDateTime endDate) {
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
			// Do some basic validations to check - to do
		}

	}
	

}
