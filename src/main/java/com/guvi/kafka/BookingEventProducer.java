package com.guvi.kafka;

import com.guvi.model.Booking;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookingEventProducer {
    private final KafkaTemplate<String,BookingEvent> kafkaTemplate;
    private final String EVENT_REGISTRATION_TOPIC = "event.registration.events";

    public BookingEventProducer(KafkaTemplate<String, BookingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

/*
*
*  private String eventName;
    private String eventId;
    private String bookingId;
    private String userId;
    private int numberOfSeats;
//    private double price;
    private Instant occurredAt;
* */
    public void publishBookingConfirmation(Booking booking){
        BookingEvent payload = new BookingEvent(
                BookingEventType.BOOKING_CONFIRMED.name(),
                booking.getEventId(),
                booking.getId(),
                booking.getUserId(),
                booking.getNumberOfSeats(),
                Instant.now()
                );

        kafkaTemplate.send(EVENT_REGISTRATION_TOPIC,booking.getEventId(),payload);

    }



}
