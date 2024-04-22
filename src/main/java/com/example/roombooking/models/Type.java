package com.example.roombooking.models;

public enum Type {
    SINGLE("Single"),
    DOUBLE("Double"),
    LARGE_DOUBLE("Large Double");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
