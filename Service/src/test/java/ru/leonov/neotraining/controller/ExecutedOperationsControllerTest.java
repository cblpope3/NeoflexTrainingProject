package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.model.*;
import ru.leonov.neotraining.services.ExecutedOperationsService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void addOperation() throws Exception {

        ExecutedOperationPostGeneratedDTO operationPost200 = new ExecutedOperationPostGeneratedDTO();

        ExecutedOperationPostGeneratedDTO operationPost404T = new ExecutedOperationPostGeneratedDTO();
        ExecutedOperationPostGeneratedDTO operationPost404W = new ExecutedOperationPostGeneratedDTO();
        ExecutedOperationPostGeneratedDTO operationPost404M = new ExecutedOperationPostGeneratedDTO();

        ExecutedOperationPostGeneratedDTO operationPost422M = new ExecutedOperationPostGeneratedDTO();
        ExecutedOperationPostGeneratedDTO operationPost422W = new ExecutedOperationPostGeneratedDTO();

        ExecutedOperationPostGeneratedDTO operationPost500 = new ExecutedOperationPostGeneratedDTO();

        operationPost200.setTechMapId(10);
        operationPost200.setWorkerId(10);
        operationPost200.setMaterialId(10);

        operationPost404T.setTechMapId(11);
        operationPost404T.setWorkerId(10);
        operationPost404T.setMaterialId(10);

        operationPost404W.setTechMapId(10);
        operationPost404W.setWorkerId(11);
        operationPost404W.setMaterialId(10);

        operationPost404M.setTechMapId(10);
        operationPost404M.setWorkerId(10);
        operationPost404M.setMaterialId(11);

        operationPost422M.setTechMapId(10);
        operationPost422M.setWorkerId(10);
        operationPost422M.setMaterialId(12);

        operationPost422W.setTechMapId(10);
        operationPost422W.setWorkerId(12);
        operationPost422W.setMaterialId(10);

        operationPost500.setTechMapId(13);
        operationPost500.setWorkerId(13);
        operationPost500.setMaterialId(13);

        when(executedOperationsService.executeOperation(operationPost200)).thenReturn(ExecutedOperationsService.STATUS_OK);
        when(executedOperationsService.executeOperation(operationPost404T)).thenReturn(ExecutedOperationsService.NO_TECH_MAP);
        when(executedOperationsService.executeOperation(operationPost404W)).thenReturn(ExecutedOperationsService.NO_WORKER);
        when(executedOperationsService.executeOperation(operationPost404M)).thenReturn(ExecutedOperationsService.NO_MATERIAL);
        when(executedOperationsService.executeOperation(operationPost422M)).thenReturn(ExecutedOperationsService.MATERIAL_NOT_MATCH);
        when(executedOperationsService.executeOperation(operationPost422W)).thenReturn(ExecutedOperationsService.WORKER_NOT_MATCH);
        when(executedOperationsService.executeOperation(operationPost500)).thenReturn(ExecutedOperationsService.NOT_SAVED);

        // check 200 response
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 10, \"workerId\": 10, \"materialId\": 10}"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 404 status not found tech map
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 11, \"workerId\": 10, \"materialId\": 10}"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("Technical map not found in database."));

        // check 404 status not found material
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 10, \"workerId\": 10, \"materialId\": 11}"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("Material not found in database."));

        // check 404 status not found worker
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 10, \"workerId\": 11, \"materialId\": 10}"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("Worker not found in database."));

        // check 422 status worker not match
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 10, \"workerId\": 12, \"materialId\": 10}"))
                .andDo(print())
                .andExpect(status().is(422))
                .andExpect(content().string("Worker don't match technical map."));

        // check 422 status material not match
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 10, \"workerId\": 10, \"materialId\": 12}"))
                .andDo(print())
                .andExpect(status().is(422))
                .andExpect(content().string("Material don't match technical map."));

        // check 500 status unknown
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 13, \"workerId\": 10, \"materialId\": 10}"))
                .andDo(print())
                .andExpect(status().is(500))
                .andExpect(content().string("Unknown error."));

        // check 500 status not saved
        this.mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"techMapId\": 13, \"workerId\": 13, \"materialId\": 13}"))
                .andDo(print())
                .andExpect(status().is(500))
                .andExpect(content().string("Executed operation haven't been saved."));
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

    @Test
    void getOperationById() throws Exception {

        when(executedOperationsService.getById(10)).thenReturn(exampleOperation1);

        // check 200 response
        this.mockMvc.perform(get("/operations/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":40,\"techMap\":{\"id\":30,\"material\":{\"id\":20,\"name\":\"mat1\"}," +
                                "\"worker\":{\"id\":10,\"name\":\"Name1\",\"lastName\":\"LastName1\"}},\"date\":\"16-05-2020\"}"));

        // check 404 response
        this.mockMvc.perform(get("/operations/11"))
                .andDo(print())
                .andExpect(status().is(404));

    }

    @Test
    void deleteOperationById() throws Exception {

        when(executedOperationsService.deleteById(10)).thenReturn(true);

        // check 200 response
        this.mockMvc.perform(delete("/operations/10"))
                .andDo(print())
                .andExpect(status().is(200));

        // check 404 response
        this.mockMvc.perform(delete("/operations/11"))
                .andDo(print())
                .andExpect(status().is(404));

    }
}