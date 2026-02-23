package com.guvi.controller;

import com.guvi.model.Event;
import com.guvi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // Create event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        event.setId(UUID.randomUUID().toString());
        event.setRemainingSeats(event.getTotalSeats());
        event.setCreatedAt(LocalDateTime.now());
        event.setStatus(true);
        return eventRepository.save(event);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable String id) {
        return eventRepository.findById(id);
    }


    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable String id) {
        eventRepository.deleteById(id);
        return "Event deleted successfully";
    }


}