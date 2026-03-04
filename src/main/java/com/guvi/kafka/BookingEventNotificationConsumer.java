package com.guvi.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventNotificationConsumer {
    private final Logger logger = LoggerFactory.getLogger(BookingEventNotificationConsumer.class);
    private final String EVENT_REGISTRATION_TOPIC = "event.registration.events";
    @KafkaListener(
        topics = EVENT_REGISTRATION_TOPIC,
        groupId = "booking-notification-group"
    )
    public void handleBookingEvent(BookingEvent bookingEvent){
        logger.info("Notification consumer received booking event:" + "booking={} ,user={]",
                bookingEvent.getBookingId(),bookingEvent.getUserId());

        logger.info("Simulate email send to userid={} for booking={}" ,
                bookingEvent.getUserId(),bookingEvent.getBookingId());

        logger.info("Notification email processing complete");
    }
}
