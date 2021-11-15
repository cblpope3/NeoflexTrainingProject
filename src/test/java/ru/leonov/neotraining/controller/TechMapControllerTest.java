package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.dto.workers_dto.WorkerDTO;
import ru.leonov.neotraining.services.TechMapService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TechMapController.class)
class TechMapControllerTest {

    private final WorkerDTO exampleWorker1 = new WorkerDTO(10, "Name1", "LastName1");
    private final WorkerDTO exampleWorker2 = new WorkerDTO(11, "Name2", "LastName2");
    private final MaterialDTO exampleMaterial1 = new MaterialDTO(20, "mat1");
    private final MaterialDTO exampleMaterial2 = new MaterialDTO(21, "mat2");
    private final TechMapDTO exampleTechMap1 = new TechMapDTO(30, exampleWorker1, exampleMaterial1);
    private final TechMapDTO exampleTechMap2 = new TechMapDTO(31, exampleWorker2, exampleMaterial2);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TechMapService techMapService;

    @Test
    void add() throws Exception {

        // check 200 response
        when(techMapService.add(any())).thenReturn(TechMapService.STATUS_OK);
        this.mockMvc.perform(post("/tech_map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"worker_id\": \"1\", \"material_id\": \"2\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 404 response "no worker"
        when(techMapService.add(any())).thenReturn(TechMapService.NO_WORKER);
        this.mockMvc.perform(post("/tech_map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"worker_id\": \"1\", \"material_id\": \"2\"}"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("Worker not found in database."));

        // check 404 response "no material"
        when(techMapService.add(any())).thenReturn(TechMapService.NO_MATERIAL);
        this.mockMvc.perform(post("/tech_map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"worker_id\": \"1\", \"material_id\": \"2\"}"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("Material not found in database."));

        // check 500 response
        when(techMapService.add(any())).thenReturn(TechMapService.NOT_SAVED);
        this.mockMvc.perform(post("/tech_map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"worker_id\": \"1\", \"material_id\": \"2\"}"))
                .andDo(print())
                .andExpect(status().is(500))
                .andExpect(content().string("Technical map not saved."));

    }

    @Test
    void getAll() throws Exception {
        List<TechMapDTO> techMapDTOS = new ArrayList<>();

        // check 204 response
        when(techMapService.getAll()).thenReturn(techMapDTOS);
        this.mockMvc.perform(get("/tech_map"))
                .andDo(print())
                .andExpect(status().is(204));

        // check 200 response
        techMapDTOS.add(exampleTechMap1);
        techMapDTOS.add(exampleTechMap2);
        when(techMapService.getAll()).thenReturn(techMapDTOS);
        this.mockMvc.perform(get("/tech_map"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[" + techMapDTOS.get(0).toJSON() + "," + techMapDTOS.get(1).toJSON() +
                        "]"));
    }

    @Test
    void getById() throws Exception {

        when(techMapService.getById(exampleTechMap1.getId())).thenReturn(exampleTechMap1);

        // check 200 response
        this.mockMvc.perform(get("/tech_map/" + exampleTechMap1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(exampleTechMap1.toJSON()));

        // check 404 response
        this.mockMvc.perform(get("/tech_map/11"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void updateById() throws Exception {

        when(techMapService.updateById(100, "120", "130")).
                thenReturn(TechMapService.STATUS_OK);
        when(techMapService.updateById(100, null, "130")).
                thenReturn(TechMapService.STATUS_OK);
        when(techMapService.updateById(100, "120", null)).
                thenReturn(TechMapService.STATUS_OK);
        when(techMapService.updateById(101, "120", "130")).
                thenReturn(TechMapService.NO_TECH_MAP);
        when(techMapService.updateById(100, "121", "130")).
                thenReturn(TechMapService.NO_WORKER);
        when(techMapService.updateById(100, "120", "131")).
                thenReturn(TechMapService.NO_MATERIAL);

        // check 200 response
        this.mockMvc.perform(put("/tech_map/100")
                        .param("workerId", "120")
                        .param("materialId", "130"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(put("/tech_map/100")
                        .param("materialId", "130"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(put("/tech_map/100")
                        .param("workerId", "120"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 404 response "No tech map"
        this.mockMvc.perform(put("/tech_map/101")
                        .param("workerId", "120")
                        .param("materialId", "130"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("Requested technical map not found in database."));

        // check 404 response "No worker"
        this.mockMvc.perform(put("/tech_map/100")
                        .param("workerId", "121")
                        .param("materialId", "130"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("New worker not found in database."));

        // check 404 response "No material"
        this.mockMvc.perform(put("/tech_map/100")
                        .param("workerId", "120")
                        .param("materialId", "131"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string("New material not found in database."));
    }

    @Test
    void deleteById() throws Exception {

        when(techMapService.deleteById(10)).thenReturn(true);

        // check 200 response
        this.mockMvc.perform(delete("/tech_map/10"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 404 response
        this.mockMvc.perform(delete("/tech_map/11"))
                .andDo(print())
                .andExpect(status().is(404));
    }
}