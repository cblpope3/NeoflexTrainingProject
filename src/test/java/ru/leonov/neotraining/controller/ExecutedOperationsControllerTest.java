package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsDTO;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.dto.workers_dto.WorkerDTO;
import ru.leonov.neotraining.services.ExecutedOperationsService;
import ru.leonov.neotraining.services.TechMapService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExecutedOperationsController.class)
class ExecutedOperationsControllerTest {

    private final WorkerDTO exampleWorker1 = new WorkerDTO(10, "Name1", "LastName1");
    private final WorkerDTO exampleWorker2 = new WorkerDTO(11, "Name2", "LastName2");
    private final MaterialDTO exampleMaterial1 = new MaterialDTO(20, "mat1");
    private final MaterialDTO exampleMaterial2 = new MaterialDTO(21, "mat2");
    private final TechMapDTO exampleTechMap1 = new TechMapDTO(30, exampleWorker1, exampleMaterial1);
    private final ExecutedOperationsDTO exampleOperation1 = new ExecutedOperationsDTO(40, exampleTechMap1, "16-05-2020");
    private final TechMapDTO exampleTechMap2 = new TechMapDTO(31, exampleWorker2, exampleMaterial2);
    private final ExecutedOperationsDTO exampleOperation2 = new ExecutedOperationsDTO(41, exampleTechMap2, "22-08-2007");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExecutedOperationsService executedOperationsService;

    @Test
    void getReport() throws Exception {
        List<ExecutedOperationsDTO> executedOperationsDTOS = new ArrayList<>();
        executedOperationsDTOS.add(exampleOperation1);
        executedOperationsDTOS.add(exampleOperation2);

        when(executedOperationsService.getAll()).thenReturn(executedOperationsDTOS);

        this.mockMvc.perform(get("/operations/ui"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("text/html;charset=UTF-8")));
    }

    @Test
    void getAll() throws Exception {
        List<ExecutedOperationsDTO> executedOperationsDTOS = new ArrayList<>();

        // check 204 response
        when(executedOperationsService.getAll()).thenReturn(executedOperationsDTOS);
        this.mockMvc.perform(get("/operations"))
                .andDo(print())
                .andExpect(status().is(204));

        // check 200 response
        executedOperationsDTOS.add(exampleOperation1);
        executedOperationsDTOS.add(exampleOperation2);
        when(executedOperationsService.getAll()).thenReturn(executedOperationsDTOS);
        this.mockMvc.perform(get("/operations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[" + executedOperationsDTOS.get(0).toJSON() + "," +
                        executedOperationsDTOS.get(1).toJSON() +
                        "]"));
    }
}