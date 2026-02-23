package com.guvi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.guvi.model.Booking;
import com.guvi.model.BookingStatus;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByUserId(String userId);

    List<Booking> findByEventId(String eventId);

    List<Booking> findByUserIdAndStatus(String userId, BookingStatus status);

    List<Booking> findByEventIdAndStatus(String eventId, BookingStatus status);

    boolean existsByUserIdAndEventIdAndStatus(
            String userId,
            String eventId,
            BookingStatus status
    );
}
