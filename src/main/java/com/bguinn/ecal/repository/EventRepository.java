package com.bguinn.ecal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bguinn.ecal.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
