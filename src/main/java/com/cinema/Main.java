package com.cinema;

import javax.swing.SwingUtilities;

import com.cinema.controller.CinemaController;
import com.cinema.model.CinemaModel;
import com.cinema.view.CinemaView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CinemaModel model = new CinemaModel();
            CinemaView view = new CinemaView(model);
            new CinemaController(model, view);
            view.setVisible(true);
        });
    }
}