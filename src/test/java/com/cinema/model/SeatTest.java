package com.cinema.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    void validSeatIsCreated() {
        Seat seat = new Seat('C', 6);
        assertEquals('C', seat.getRow());
        assertEquals(6, seat.getNumber());
        assertEquals("C6", seat.toString());
    }

    @Test
    void invalidRowIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Seat('Z', 1));
    }

    @Test
    void invalidNumberIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Seat('A', 0));
        assertThrows(IllegalArgumentException.class, () -> new Seat('A', 11));
    }

    @Test
    void equalSeatsAreEqualWithSameHash() {
        Seat a = new Seat('B', 3);
        Seat b = new Seat('B', 3);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void setFindsAnEqualSeat() {
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat('B', 3));
        assertTrue(seats.contains(new Seat('B', 3)));
    }
}