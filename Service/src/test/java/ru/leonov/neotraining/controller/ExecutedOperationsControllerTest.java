package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.model.ExecutedOperationGeneratedDTO;
import ru.leonov.neotraining.model.MaterialGeneratedDTO;
import ru.leonov.neotraining.model.TechMapGeneratedDTO;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.services.ExecutedOperationsService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ru.leonov.neotraining.controller.ExecutedOperationsController.class)
class ExecutedOperationsControllerTest {

    private static final WorkerGeneratedDTO exampleWorker1 = new WorkerGeneratedDTO();
    private static final WorkerGeneratedDTO exampleWorker2 = new WorkerGeneratedDTO();
    private static final MaterialGeneratedDTO exampleMaterial1 = new MaterialGeneratedDTO();
    private static final MaterialGeneratedDTO exampleMaterial2 = new MaterialGeneratedDTO();
    private static final TechMapGeneratedDTO exampleTechMap1 = new TechMapGeneratedDTO();
    private static final ExecutedOperationGeneratedDTO exampleOperation1 = new ExecutedOperationGeneratedDTO();
    private static final TechMapGeneratedDTO exampleTechMap2 = new TechMapGeneratedDTO();
    private static final ExecutedOperationGeneratedDTO exampleOperation2 = new ExecutedOperationGeneratedDTO();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExecutedOperationsService executedOperationsService;

    @BeforeAll
    static void setUpAll() {
        exampleWorker1.setId(10);
        exampleWorker1.setName("Name1");
        exampleWorker1.setLastName("LastName1");

        exampleWorker2.setId(11);
        exampleWorker2.setName("Name2");
        exampleWorker2.setLastName("LastName2");

        exampleMaterial1.setId(20);
        exampleMaterial1.setName("mat1");

        exampleMaterial2.setId(21);
        exampleMaterial2.setName("mat2");

        exampleTechMap1.setId(30);
        exampleTechMap1.setWorker(exampleWorker1);
        exampleTechMap1.setMaterial(exampleMaterial1);

        exampleTechMap2.setId(31);
        exampleTechMap2.setWorker(exampleWorker2);
        exampleTechMap2.setMaterial(exampleMaterial2);

        exampleOperation1.setId(40);
        exampleOperation1.setTechMap(exampleTechMap1);
        exampleOperation1.setDate("16-05-2020");

        exampleOperation2.setId(41);
        exampleOperation2.setTechMap(exampleTechMap2);
        exampleOperation2.setDate("22-08-2007");
    }

    @Test
    void getReport() throws Exception {
        Set<ExecutedOperationGeneratedDTO> executedOperationsDTOS = new HashSet<>();
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
        Set<ExecutedOperationGeneratedDTO> executedOperationsDTOS = new HashSet<>();

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
                .andExpect(content().json(
                        "[{\"id\":41,\"techMap\":{\"id\":31,\"material\":{\"id\":21,\"name\":\"mat2\"}," +
                                "\"worker\":{\"id\":11,\"name\":\"Name2\",\"lastName\":\"LastName2\"}},\"date\":\"22-08-2007\"}," +
                                "{\"id\":40,\"techMap\":{\"id\":30,\"material\":{\"id\":20,\"name\":\"mat1\"}," +
                                "\"worker\":{\"id\":10,\"name\":\"Name1\",\"lastName\":\"LastName1\"}},\"date\":\"16-05-2020\"}]"));
    }
}