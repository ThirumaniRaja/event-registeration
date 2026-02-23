package com.guvi.repository;
import com.guvi.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByLocation(String location);
    List<Event> findByDate(LocalDate date);
    List<Event> findByTitleContainingIgnoreCase(String title);
}
