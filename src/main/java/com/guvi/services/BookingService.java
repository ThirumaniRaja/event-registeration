package com.guvi.services;




import com.guvi.model.Booking;
import com.guvi.model.BookingStatus;
import com.guvi.model.Event;
import com.guvi.repository.BookingRepository;
import com.guvi.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
    }

    public Booking book(Booking booking) {

        // 1️⃣ Find event
        Event event = eventRepository.findById(booking.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // 2️⃣ Prevent duplicate confirmed booking for same user + event
        if (bookingRepository.existsByUserIdAndEventIdAndStatus(
                booking.getUserId(),
                booking.getEventId(),
                BookingStatus.CONFIRMED)) {

            throw new RuntimeException("User already has booking for this event");
        }


        if (event.getRemainingSeats() < booking.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats available");
        }

        // 4️⃣ Reduce seats
        event.setRemainingSeats(
                event.getRemainingSeats() - booking.getNumberOfSeats()
        );
        eventRepository.save(event);

        // 5️⃣ Set booking details
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(LocalDateTime.now());

        // 6️⃣ Save booking
        return bookingRepository.save(booking);
    }
}
