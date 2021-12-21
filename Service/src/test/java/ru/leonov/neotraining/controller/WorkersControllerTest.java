package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.services.WorkersService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkersController.class)
class WorkersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkersService workersService;

    @Test
    void add() throws Exception {
        when(workersService.add(any())).thenReturn(true);

        // check 200 response
        this.mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\": \"TestLastName\", \"name\": \"TestName\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 400 response
        this.mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestName\"}"))
                .andDo(print())
                .andExpect(status().is(400));
        this.mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\": \"TestLastName\"}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void getAll() throws Exception {
        Set<WorkerGeneratedDTO> workerDTOS = new HashSet<>();

        // check 204 response
        when(workersService.getAll()).thenReturn(workerDTOS);
        this.mockMvc.perform(get("/worker"))
                .andDo(print())
                .andExpect(status().is(204));

        // check 200 response
        WorkerGeneratedDTO workerGeneratedDTO1 = new WorkerGeneratedDTO();
        WorkerGeneratedDTO workerGeneratedDTO2 = new WorkerGeneratedDTO();

        workerGeneratedDTO1.setId(1323);
        workerGeneratedDTO1.setName("TestName1");
        workerGeneratedDTO1.setLastName("TestLastName1");

        workerGeneratedDTO2.setId(124);
        workerGeneratedDTO2.setName("TestName2");
        workerGeneratedDTO2.setLastName("TestLastName2");

        workerDTOS.add(workerGeneratedDTO1);
        workerDTOS.add(workerGeneratedDTO2);
        when(workersService.getAll()).thenReturn(workerDTOS);
        this.mockMvc.perform(get("/worker"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1323, \"name\":\"TestName1\", \"lastName\":\"TestLastName1\"}," +
                        "{\"id\":124, \"name\":\"TestName2\", \"lastName\":\"TestLastName2\"}]"));
    }

    @Test
    void getById() throws Exception {
        WorkerGeneratedDTO workerDTO = new WorkerGeneratedDTO();
        workerDTO.setId(10);
        workerDTO.setName("TestName");
        workerDTO.setLastName("TestLastName");
        when(workersService.getById(10)).thenReturn(workerDTO);

        // check 200 response
        this.mockMvc.perform(get("/worker/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":10, \"name\":\"TestName\", \"lastName\":\"TestLastName\"}"));

        // check 404 response
        this.mockMvc.perform(get("/worker/11"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void updateById() throws Exception {

        when(workersService.updateById(10, "TestName", "TestLastName")).thenReturn(true);
        when(workersService.updateById(10, "TestName", null)).thenReturn(true);
        when(workersService.updateById(10, null, "TestLastName")).thenReturn(true);

        // check 200 response
        this.mockMvc.perform(put("/worker/10")
                        .param("name", "TestName")
                        .param("lastName", "TestLastName"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(put("/worker/10")
                        .param("lastName", "TestLastName"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(put("/worker/10")
                        .param("name", "TestName"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 404 response
        this.mockMvc.perform(put("/worker/11")
                        .param("name", "TestName")
                        .param("lastName", "TestLastName"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void deleteById() throws Exception {

        when(workersService.deleteById(10)).thenReturn(true);
        // check 200 response
        this.mockMvc.perform(delete("/worker/10"))
                .andDo(print())
                .andExpect(status().isOk());
        // check 404 response
        this.mockMvc.perform(delete("/worker/11"))
                .andDo(print())
                .andExpect(status().is(404));
    }
}