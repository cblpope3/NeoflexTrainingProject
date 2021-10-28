package ru.leonov.neotraining.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.services.TestService;
import ru.leonov.neotraining.services.WorkersService;

@Controller
@RequestMapping(value = "/test")
public class MVCController {

    @Autowired
    private TestService testService;

    @GetMapping("")
    @ResponseBody
    public String getTestPage(){
        return testService.getTestMessage();
    }
}
