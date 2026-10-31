package com.cinema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.cinema.model.CinemaModel;
import com.cinema.model.ModelListener;
import com.cinema.model.Seat;

public class CinemaView extends JFrame implements ModelListener {

    private static final int ROWS = Seat.LAST_ROW - 'A' + 1;
    private static final Color FREE_COLOR = new Color(200, 230, 200);
    private static final Color SELECTED_COLOR = new Color(255, 210, 80);
    private static final Color BOOKED_COLOR = new Color(220, 90, 90);

    private final CinemaModel model;

    private final JComboBox<String> showtimeBox = new JComboBox<>();
    private final JButton[][] seatButtons = new JButton[ROWS][Seat.SEATS_PER_ROW];
    private final JLabel summaryLabel = new JLabel();
    private final JButton confirmButton = new JButton("Confirm booking");
    private final JComboBox<Integer> bookingBox = new JComboBox<>();
    private final JButton cancelButton = new JButton("Cancel booking");

    public CinemaView(CinemaModel model) {
        super("Cinema Seat Booking");
        this.model = model;

        for (String showtime : model.getShowtimes()) {
            showtimeBox.addItem(showtime);
        }

        setLayout(new BorderLayout(10, 10));
        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildSeatPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        model.addListener(this);
        refresh();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    // ----- Layout -----
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Showtime:"));
        panel.add(showtimeBox);
        return panel;
    }

    private JPanel buildSeatPanel() {
        JPanel grid = new JPanel(new GridLayout(ROWS, Seat.SEATS_PER_ROW + 1, 4, 4));
        for (int r = 0; r < ROWS; r++) {
            char row = (char) ('A' + r);
            grid.add(new JLabel(String.valueOf(row), SwingConstants.CENTER));
            for (int n = 1; n <= Seat.SEATS_PER_ROW; n++) {
                JButton button = new JButton(String.valueOf(n));
                button.setFocusPainted(false);
                button.setOpaque(true);
                seatButtons[r][n - 1] = button;
                grid.add(button);
            }
        }

        JLabel screen = new JLabel("SCREEN", SwingConstants.CENTER);
        screen.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel.add(screen, BorderLayout.NORTH);
        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(summaryLabel);
        panel.add(confirmButton);
        panel.add(new JLabel("   Booking #"));
        panel.add(bookingBox);
        panel.add(cancelButton);
        return panel;
    }

    // ----- Observer -----
    @Override
    public void modelChanged() {
        refresh();
    }

    private void refresh() {
        for (int r = 0; r < ROWS; r++) {
            for (int n = 1; n <= Seat.SEATS_PER_ROW; n++) {
                Seat seat = new Seat((char) ('A' + r), n);
                JButton button = seatButtons[r][n - 1];
                if (model.isBooked(seat)) {
                    button.setBackground(BOOKED_COLOR);
                } else if (model.isSelected(seat)) {
                    button.setBackground(SELECTED_COLOR);
                } else {
                    button.setBackground(FREE_COLOR);
                }
            }
        }

        summaryLabel.setText(model.getSelectedCount() + " seats  |  Total: "
                + model.getTotalPrice() + " SAR");
        confirmButton.setEnabled(model.getSelectedCount() > 0);

        bookingBox.removeAllItems();
        for (int number : model.getBookingNumbers()) {
            bookingBox.addItem(number);
        }
        cancelButton.setEnabled(bookingBox.getItemCount() > 0);
    }

    // ----- Hooks for the Controller -----
    public void addShowtimeListener(ActionListener listener) {
        showtimeBox.addActionListener(listener);
    }

    public void addSeatListener(Consumer<Seat> listener) {
        for (int r = 0; r < ROWS; r++) {
            for (int n = 1; n <= Seat.SEATS_PER_ROW; n++) {
                Seat seat = new Seat((char) ('A' + r), n);
                seatButtons[r][n - 1].addActionListener(e -> listener.accept(seat));
            }
        }
    }

    public void addConfirmListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public String getSelectedShowtime() {
        return (String) showtimeBox.getSelectedItem();
    }

    public Integer getSelectedBookingNumber() {
        return (Integer) bookingBox.getSelectedItem();
    }
}