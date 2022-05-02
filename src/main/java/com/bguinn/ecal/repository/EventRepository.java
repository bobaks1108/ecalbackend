package com.bguinn.ecal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bguinn.ecal.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	// retrieves events created before a given date time
	@Query("select a from Event a where startDate <= :filterStartDateTime")
	List<Event> findAllWithStartDateTimeBefore(
			@Param("filterStartDateTime") Date filterStartDateTime);

}
