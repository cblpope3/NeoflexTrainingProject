package ru.leonov.neotraining.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.leonov.neotraining.model.MaterialGeneratedDTO;
import ru.leonov.neotraining.services.MaterialService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaterialController.class)
class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialService materialService;

    @Test
    void add() throws Exception {
        when(materialService.add(any())).thenReturn(true);

        // check 200 response
        this.mockMvc.perform(post("/material")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestName\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 400 response
        this.mockMvc.perform(post("/material")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void getAll() throws Exception {
        Set<MaterialGeneratedDTO> materialDTOS = new HashSet<>();

        // check 204 response
        when(materialService.getAll()).thenReturn(materialDTOS);
        this.mockMvc.perform(get("/material"))
                .andDo(print())
                .andExpect(status().is(204));

        // check 200 response
        MaterialGeneratedDTO material1 = new MaterialGeneratedDTO();
        MaterialGeneratedDTO material2 = new MaterialGeneratedDTO();

        material1.setId(1323);
        material1.setName("TestName1");

        material2.setId(124);
        material2.setName("TestName2");

        materialDTOS.add(material1);
        materialDTOS.add(material2);
        when(materialService.getAll()).thenReturn(materialDTOS);
        this.mockMvc.perform(get("/material"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1323, \"name\":\"TestName1\"}," +
                        "{\"id\":124, \"name\":\"TestName2\"}]"));
    }

    @Test
    void getById() throws Exception {
        MaterialGeneratedDTO materialDTO = new MaterialGeneratedDTO();
        materialDTO.setId(10);
        materialDTO.setName("TestName");

        when(materialService.getById(10)).thenReturn(materialDTO);

        // check 200 response
        this.mockMvc.perform(get("/material/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":10, \"name\":\"TestName\"}"));

        // check 404 response
        this.mockMvc.perform(get("/material/11"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void updateById() throws Exception {

        when(materialService.updateById(10, "TestName")).thenReturn(true);
        when(materialService.updateById(10, null)).thenReturn(true);

        // check 200 response
        this.mockMvc.perform(put("/material/10")
                        .param("name", "TestName"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(put("/material/10"))
                .andDo(print())
                .andExpect(status().isOk());

        // check 404 response
        this.mockMvc.perform(put("/material/11")
                        .param("name", "TestName")
                        .param("lastName", "TestLastName"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void deleteById() throws Exception {

        when(materialService.deleteById(10)).thenReturn(true);
        // check 200 response
        this.mockMvc.perform(delete("/material/10"))
                .andDo(print())
                .andExpect(status().isOk());
        // check 404 response
        this.mockMvc.perform(delete("/material/11"))
                .andDo(print())
                .andExpect(status().is(404));
    }
}