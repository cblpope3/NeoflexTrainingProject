package ru.leonov.neotraining.services;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String getTestMessage() {
        return "OK";
    }
}