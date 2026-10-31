package com.cinema.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CinemaModelTest {

    private CinemaModel model;
    private final Seat c6 = new Seat('C', 6);

    @BeforeEach
    void setUp() {
        model = new CinemaModel();
    }

    @Test
    void clickingTwiceSelectsThenDeselects() {
        model.toggleSeat(c6);
        assertTrue(model.isSelected(c6));
        model.toggleSeat(c6);
        assertFalse(model.isSelected(c6));
    }

    @Test
    void totalIsSeatCountTimesPrice() {
        model.toggleSeat(c6);
        model.toggleSeat(new Seat('C', 7));
        assertEquals(2 * CinemaModel.SEAT_PRICE, model.getTotalPrice());
    }

    @Test
    void confirmBooksSeatsAndClearsSelection() {
        model.toggleSeat(c6);
        model.confirmBooking();
        assertTrue(model.isBooked(c6));
        assertEquals(0, model.getSelectedCount());
        assertEquals(1, model.getBookingNumbers().size());
    }

    @Test
    void bookedSeatCannotBeSelected() {
        model.toggleSeat(c6);
        model.confirmBooking();
        model.toggleSeat(c6);
        assertFalse(model.isSelected(c6));
    }

    @Test
    void cancelFreesTheSeats() {
        model.toggleSeat(c6);
        model.confirmBooking();
        int number = model.getBookingNumbers().get(0);
        model.cancelBooking(number);
        assertFalse(model.isBooked(c6));
    }

    @Test
    void showtimesHaveSeparateBookings() {
        model.toggleSeat(c6);
        model.confirmBooking();
        model.selectShowtime(model.getShowtimes().get(1));
        assertFalse(model.isBooked(c6));
    }

    @Test
    void listenerIsNotifiedOnChange() {
        int[] calls = {0};
        model.addListener(() -> calls[0]++);
        model.toggleSeat(c6);
        assertEquals(1, calls[0]);
    }
}