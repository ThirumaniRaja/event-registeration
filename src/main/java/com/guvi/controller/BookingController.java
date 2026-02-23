package com.guvi.controller;

import com.guvi.model.Booking;
import com.guvi.model.BookingStatus;
import com.guvi.model.Event;
import com.guvi.repository.BookingRepository;
import com.guvi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    // Book seats
    @PostMapping
    public String createBooking(@RequestBody Booking bookingRequest) {

        Optional<Event> eventOpt = eventRepository.findById(bookingRequest.getEventId());

        if (eventOpt.isEmpty()) {
            return "Event not found";
        }

        Event event = eventOpt.get();

        if (!event.isStatus()) {
            return "Event is not active";
        }

        if (event.getRemainingSeats() < bookingRequest.getNumberOfSeats()) {
            return "Not enough seats available";
        }

        // Reduce seats
        event.setRemainingSeats(event.getRemainingSeats() - bookingRequest.getNumberOfSeats());
        eventRepository.save(event);

        // Save booking
//        Booking booking = new Booking();
//        booking.setId(UUID.randomUUID().toString());
//        booking.setEventId(bookingRequest.getEventId());
//        booking.setUserId(bookingRequest.getUserId());
//        booking.setNumberOfSeats(bookingRequest.getNumberOfSeats());
//        booking.setStatus(BookingStatus.CONFIRMED);
//        booking.setCreatedAt(LocalDateTime.now());
        Booking booking = new Booking(
                UUID.randomUUID().toString(),
                bookingRequest.getEventId(),
                bookingRequest.getUserId(),
                bookingRequest.getNumberOfSeats()
        );

        bookingRepository.save(booking);

        return "Booking confirmed with id: " + booking.getId();
    }

    // Cancel booking
    @PutMapping("/{bookingId}/cancel")
    public String cancelBooking(@PathVariable String bookingId) {

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            return "Booking not found";
        }

        Booking booking = bookingOpt.get();

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return "Booking already cancelled";
        }

        // restore seats
        Optional<Event> eventOpt = eventRepository.findById(booking.getEventId());
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.setRemainingSeats(event.getRemainingSeats() + booking.getNumberOfSeats());
            eventRepository.save(event);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return "Booking cancelled successfully";
    }

    // Get bookings by user
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable String userId) {
        return bookingRepository.findByUserId(userId);
    }

    // Get bookings by event
    @GetMapping("/event/{eventId}")
    public List<Booking> getBookingsByEvent(@PathVariable String eventId) {
        return bookingRepository.findByEventId(eventId);
    }

    // Get bookings by user and status
    @GetMapping("/user/{userId}/status")
    public List<Booking> getUserBookingsByStatus(
            @PathVariable String userId,
            @RequestParam BookingStatus status) {

        return bookingRepository.findByUserIdAndStatus(userId, status);
    }

    // Get bookings by event and status
    @GetMapping("/event/{eventId}/status")
    public List<Booking> getEventBookingsByStatus(
            @PathVariable String eventId,
            @RequestParam BookingStatus status) {

        return bookingRepository.findByEventIdAndStatus(eventId, status);
    }
}