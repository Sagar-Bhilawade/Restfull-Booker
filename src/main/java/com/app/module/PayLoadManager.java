package com.app.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.app.payload.Booking;
import com.app.payload.BookingDates;

public class PayLoadManager {
    ObjectMapper objectMapper;

    public String createPayload() throws JsonProcessingException {
        Faker faker = new Faker();
        objectMapper = new ObjectMapper();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname("Bhilawade");
        booking.setTotalprice(10000);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast ,Lunch");
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2023-03-03");
        bookingDates.setCheckout("2023-05-05");
        booking.setBookingdates(bookingDates);
        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
        return payload;

    }

    public String updatedPayload() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        Booking booking = new Booking();
        booking.setFirstname("Shagie");
        booking.setLastname("Boss");
        booking.setTotalprice(199);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast, lunch");
        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-10-01");
        booking.setBookingdates(bookingdates);
        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
        return payload;
    }

    public String updatePayload() {
        return null;
    }
}
