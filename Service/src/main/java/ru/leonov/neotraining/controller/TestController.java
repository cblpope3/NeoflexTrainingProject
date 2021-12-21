package ru.leonov.neotraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.leonov.neotraining.services.TestService;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("")
    @ResponseBody
    public String getTestPage() {
        return testService.getTestMessage();
    }
}
