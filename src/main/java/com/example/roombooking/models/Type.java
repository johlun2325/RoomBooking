package com.example.roombooking.models;

public enum Type {
    SINGLE(1),
    SINGLE_LARGE(2),
    DOUBLE(3),
    DOUBLE_LARGE(4);

    private final double capacity;

    Type(double capacity) {
        this.capacity = capacity;

    }

    public double capacity() {
        return capacity;
    }
}
