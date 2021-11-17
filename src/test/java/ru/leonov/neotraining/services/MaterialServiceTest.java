package ru.leonov.neotraining.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.material_dto.MaterialPostDTO;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.mappers.MaterialMapperImpl;
import ru.leonov.neotraining.repositories.MaterialRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MaterialServiceTest {

    private final MaterialEntity testMaterialEntity1 = new MaterialEntity("testName1");
    private final MaterialEntity testMaterialEntity2 = new MaterialEntity("testName2");
    private final MaterialPostDTO testMaterialPostDTO1 = new MaterialPostDTO("testName1");
    private final MaterialDTO testMaterialDTO1 = new MaterialDTO(11, "testName1");
    private final MaterialDTO testMaterialDTO2 = new MaterialDTO(12, "testName2");
    @MockBean
    MaterialRepository materialRepository;
    @MockBean
    MaterialMapperImpl materialMapper;
    MaterialService materialService;

    @BeforeEach
    void setUp() {
        materialService = new MaterialService(materialRepository, materialMapper);
    }

    @AfterEach
    void tearDown() {
        clearInvocations(materialRepository);
    }

    @Test
    void add() {
        when(materialMapper.materialPostDtoToMaterialEntity(testMaterialPostDTO1)).thenReturn(testMaterialEntity1);
        when(materialRepository.save(testMaterialEntity1)).thenReturn(testMaterialEntity1);

        materialService.add(testMaterialPostDTO1);

        verify(materialRepository, times(1)).save(testMaterialEntity1);
    }

    @Test
    void getAll() {
        List<MaterialDTO> testListDTO = new ArrayList<>();

        testListDTO.add(testMaterialDTO1);
        testListDTO.add(testMaterialDTO2);

        List<MaterialEntity> testListEntity = new ArrayList<>();

        testListEntity.add(testMaterialEntity1);
        testListEntity.add(testMaterialEntity2);

        when(materialRepository.findAll()).thenReturn(testListEntity);
        when(materialMapper.materialsToMaterialsAllDto(testListEntity)).thenReturn(testListDTO);

        assertEquals(testListDTO, materialService.getAll());
        verify(materialRepository, times(1)).findAll();
    }

    @Test
    void getById() {

        int materialId = 10;
        int wrongMaterialId = 11;

        when(materialRepository.findById(materialId)).thenReturn(testMaterialEntity1);
        when(materialRepository.existsById(materialId)).thenReturn(true);
        when(materialRepository.existsById(wrongMaterialId)).thenReturn(false);
        when(materialMapper.materialEntityToMaterialDto(testMaterialEntity1)).thenReturn(testMaterialDTO1);

        // test to find existing materialDTO
        assertEquals(testMaterialDTO1, materialService.getById(materialId));
        // test if materialDTO not exist
        assertNull(materialService.getById(wrongMaterialId));

        verify(materialRepository, times(1)).findById(materialId);
    }

    @Test
    void updateById() {
        String newName = testMaterialDTO2.getName();
        int materialId = 10;
        int wrongMaterialId = 11;

        when(materialRepository.existsById(materialId)).thenReturn(true);
        when(materialRepository.existsById(wrongMaterialId)).thenReturn(false);
        when(materialRepository.findById(materialId)).thenReturn(testMaterialEntity1);
        when(materialRepository.save(testMaterialEntity1)).thenReturn(testMaterialEntity1);
        when(materialRepository.save(testMaterialEntity2)).thenReturn(testMaterialEntity2);

        // test to update existing materialDTO
        assertTrue(materialService.updateById(materialId, newName));
        // test if materialDTO not exist
        assertFalse(materialService.updateById(wrongMaterialId, newName));

        verify(materialRepository, times(1)).save(testMaterialEntity1);
    }

    @Test
    void deleteById() {
        int materialId = 10;
        int wrongMaterialId = 11;

        when(materialRepository.existsById(materialId)).thenReturn(true);
        when(materialRepository.existsById(wrongMaterialId)).thenReturn(false);

        // test to update existing materialDTO
        assertTrue(materialService.deleteById(materialId));

        // test if materialDTO not exist
        assertFalse(materialService.deleteById(wrongMaterialId));

        verify(materialRepository, times(1)).deleteById(materialId);
    }
}