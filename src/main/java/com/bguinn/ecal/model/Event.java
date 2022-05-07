package com.bguinn.ecal.model;

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
	private String name;
	
	@Column(name="start_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date startDate;
	
	@Column(name="end_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date endDate;
	
    public Event() {
		super();
	}

	private Event(EventBuilder builder) {
		this.name = builder.name;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public static class EventBuilder
	{
		private String name;
		private Date startDate;
		private Date endDate;

		public EventBuilder(String name) {
			this.name = name;
		}
		
		public EventBuilder startDate(Date startDate) {
			this.startDate = startDate;
			return this;
		}
		
		public EventBuilder endDate(Date endDate) {
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
