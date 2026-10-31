package com.cinema.model;

public interface Subject {
    void addListener(ModelListener listener);
    void removeListener(ModelListener listener);
    void notifyListeners();
}