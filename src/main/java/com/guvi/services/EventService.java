package com.guvi.services;




import com.guvi.model.Event;
import com.guvi.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ✅ Create event
    public Event createEvent(Event event) {

        if (event.getTotalSeats() <= 0) {
            throw new RuntimeException("Total seats must be greater than 0");
        }

        // initially remaining seats = total seats
        event.setRemainingSeats(event.getTotalSeats());

        return eventRepository.save(event);
    }

    // ✅ Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // ✅ Get single event
    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

//    // ✅ Delete event
//    public void deleteEvent(String id) {
//        if (!eventRepository.existsById(id)) {
//            throw new RuntimeException("Event not found");
//        }
//        eventRepository.deleteById(id);
//    }


    public List<Event> searchEvents(String location, String title, LocalDate eventDate){

        return eve
    }

}
