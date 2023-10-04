package com.bguinn.ecal.repository;

import com.bguinn.ecal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // retrieves events created before a given date time
    @Query("select a from Event a where startDate <= :filterStartDateTime AND startDate >= CURDATE()")
    List<Event> findAllWithStartDateTimeOnOrBefore(
            @Param("filterStartDateTime") LocalDate filterStartDateTime);

    @Query("select e from Event e where lower(e.eventName) like CONCAT('%', lower(:filterText), '%')")
    List<Event> findByName(
            @Param("filterText") String filterText);

}
