package ru.leonov.neotraining.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceTest {

    private final TestService testService = new TestService();

    @Test
    void getTestMessage() {
        String testMessage = testService.getTestMessage();
        assertEquals("OK", testMessage);
    }
}