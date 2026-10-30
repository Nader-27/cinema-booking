package com.cinema.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CinemaModel {

    public static final int SEAT_PRICE = 40;

    private final List<String> showtimes = List.of("4:00 PM", "7:00 PM", "10:00 PM");
    // showtime -> (booking number -> seats in that booking)
    private final Map<String, Map<Integer, Set<Seat>>> bookings = new HashMap<>();
    private final Set<Seat> selectedSeats = new HashSet<>();
    private final List<ModelListener> listeners = new ArrayList<>();

    private String currentShowtime;
    private int nextBookingNumber = 1;

    public CinemaModel() {
        for (String showtime : showtimes) {
            bookings.put(showtime, new LinkedHashMap<>());
        }
        currentShowtime = showtimes.get(0);
    }

    // ----- Observer -----
    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.modelChanged();
        }
    }

    // ----- Showtimes (FR1) -----
    public List<String> getShowtimes() {
        return showtimes;
    }

    public String getCurrentShowtime() {
        return currentShowtime;
    }

    public void selectShowtime(String showtime) {
        if (!showtimes.contains(showtime)) {
            throw new IllegalArgumentException("Unknown showtime: " + showtime);
        }
        currentShowtime = showtime;
        selectedSeats.clear();
        notifyListeners();
    }

    // ----- Seats (FR2, FR3, FR4) -----
    public boolean isBooked(Seat seat) {
        for (Set<Seat> bookedSeats : bookings.get(currentShowtime).values()) {
            if (bookedSeats.contains(seat)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(Seat seat) {
        return selectedSeats.contains(seat);
    }

    public void toggleSeat(Seat seat) {
        if (isBooked(seat)) {
            return;
        }
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
        } else {
            selectedSeats.add(seat);
        }
        notifyListeners();
    }

    // ----- Totals (FR5) -----
    public int getSelectedCount() {
        return selectedSeats.size();
    }

    public int getTotalPrice() {
        return selectedSeats.size() * SEAT_PRICE;
    }

    // ----- Booking (FR6, FR7) -----
    public void confirmBooking() {
        if (selectedSeats.isEmpty()) {
            return;
        }
        bookings.get(currentShowtime).put(nextBookingNumber, new HashSet<>(selectedSeats));
        nextBookingNumber++;
        selectedSeats.clear();
        notifyListeners();
    }

    public List<Integer> getBookingNumbers() {
        return new ArrayList<>(bookings.get(currentShowtime).keySet());
    }

    public void cancelBooking(int bookingNumber) {
        bookings.get(currentShowtime).remove(bookingNumber);
        notifyListeners();
    }
}
