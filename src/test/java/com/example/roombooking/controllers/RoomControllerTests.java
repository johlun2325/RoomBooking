package com.example.roombooking.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoomControllerTests {
    @Autowired
    private RoomController roomController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(roomController).isNotNull();
    }
    @Test
    void getAllRooms() {
    }

    @Test
    void getRoom() {
    }

    @Test
    void searchRooms() {
    }
}