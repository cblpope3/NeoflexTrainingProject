package ru.leonov.neotraining.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.leonov.neotraining.services.TestService;

@Controller
public class MVCController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    @ResponseBody
    public String getTestPage(){
        return testService.getTestMessage();
    }
}
