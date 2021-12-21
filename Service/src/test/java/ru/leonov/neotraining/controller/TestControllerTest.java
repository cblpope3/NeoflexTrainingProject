package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.services.TestService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    void testControllerTest() throws Exception {
        when(testService.getTestMessage()).thenReturn("OK");
        this.mockMvc.perform(get("/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
}