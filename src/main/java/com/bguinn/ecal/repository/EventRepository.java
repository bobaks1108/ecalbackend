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
	List<Event> findAllWithStartDateTimeOnOrBefore(
			@Param("filterStartDateTime") Date filterStartDateTime);
	
	@Query("select e from Event e where lower(e.name) like CONCAT('%', lower(:filterText), '%')")
	List<Event> findByName(
			@Param("filterText") String filterText);
	
//    @RestResource(path = "name", rel="name")
//    @Query("from Hero h where lower(h.name) like CONCAT('%', lower(:contains), '%')")
//    public Iterable<Hero> findByName(@Param("contains") String name);  

}
