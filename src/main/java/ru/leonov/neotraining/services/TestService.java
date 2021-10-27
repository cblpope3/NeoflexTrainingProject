package ru.leonov.neotraining.services;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final String TEST_MESSAGE = "OK";

    public String getTestMessage(){
        return TEST_MESSAGE;
    }
}
