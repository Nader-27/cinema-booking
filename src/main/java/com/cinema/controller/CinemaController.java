package com.cinema.controller;

import com.cinema.model.CinemaModel;
import com.cinema.view.CinemaView;

public class CinemaController {

    private final CinemaModel model;
    private final CinemaView view;

    public CinemaController(CinemaModel model, CinemaView view) {
        this.model = model;
        this.view = view;

        view.addShowtimeListener(e -> model.selectShowtime(view.getSelectedShowtime()));
        view.addSeatListener(seat -> model.toggleSeat(seat));
        view.addConfirmListener(e -> model.confirmBooking());
        view.addCancelListener(e -> cancelSelected());
    }

    private void cancelSelected() {
        Integer number = view.getSelectedBookingNumber();
        if (number != null) {
            model.cancelBooking(number);
        }
    }
}