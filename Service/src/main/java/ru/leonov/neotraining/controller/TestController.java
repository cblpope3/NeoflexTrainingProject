package ru.leonov.neotraining.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.leonov.neotraining.services.TestService;

@Controller
@RequestMapping(value = "/test")
@Api
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "Test controller.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Return 'OK' response to GET request.",
            response = String.class)})
    @GetMapping("")
    @ResponseBody
    public String getTestPage() {
        return testService.getTestMessage();
    }
}
