package com.cinema.model;

import java.util.Objects;

public final class Seat {

    public static final char LAST_ROW = 'H';
    public static final int SEATS_PER_ROW = 10;

    private final char row;
    private final int number;

    // RI: 'A' <= row <= LAST_ROW, 1 <= number <= SEATS_PER_ROW
    // AF: represents the chair at row 'row', position 'number' e.g. C6
    // Safety from rep exposure: fields are private, final, and primitive, and there
    // are no setters, so no one outside can change them

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
        checkRep();
    }

    private void checkRep() {
        if (row < 'A' || row > LAST_ROW || number < 1 || number > SEATS_PER_ROW) {
            throw new IllegalArgumentException("Invalid seat: " + row + number);

        }

    }

    public char getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object other) {

        if (this == other)
            return true;
        if (!(other instanceof Seat))
            return false;
        Seat that = (Seat) other;
        return this.row == that.row && this.number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, number);
    }

    @Override
    public String toString() {
        return "" + row + number;
    }

}
