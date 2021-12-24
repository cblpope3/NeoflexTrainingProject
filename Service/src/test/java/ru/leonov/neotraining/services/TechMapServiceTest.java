package ru.leonov.neotraining.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.TechMapMapperImpl;
import ru.leonov.neotraining.model.MaterialGeneratedDTO;
import ru.leonov.neotraining.model.TechMapGeneratedDTO;
import ru.leonov.neotraining.model.TechMapPostGeneratedDTO;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TechMapServiceTest {

    private static final WorkerEntity testWorkerEntity1 = new WorkerEntity(11, "testName1", "testLastName1");
    private static final WorkerEntity testWorkerEntity2 = new WorkerEntity(12, "testName2", "testLastName2");
    private static final WorkerGeneratedDTO testWorkerDTO1 = new WorkerGeneratedDTO();
    private static final WorkerGeneratedDTO testWorkerDTO2 = new WorkerGeneratedDTO();

    private static final MaterialEntity testMaterialEntity1 = new MaterialEntity(21, "testName1");
    private static final MaterialEntity testMaterialEntity2 = new MaterialEntity(22, "testName2");
    private static final MaterialGeneratedDTO testMaterialDTO1 = new MaterialGeneratedDTO();
    private static final MaterialGeneratedDTO testMaterialDTO2 = new MaterialGeneratedDTO();

    private static final TechMapEntity testTechMapEntity1 = new TechMapEntity(31, testWorkerEntity1, testMaterialEntity1);
    private static final TechMapEntity testTechMapEntity2 = new TechMapEntity(32, testWorkerEntity2, testMaterialEntity2);
    private static final TechMapPostGeneratedDTO testTechMapPostDTO1 = new TechMapPostGeneratedDTO();
    private static final TechMapGeneratedDTO testTechMapDTO1 = new TechMapGeneratedDTO();
    private static final TechMapGeneratedDTO testTechMapDTO2 = new TechMapGeneratedDTO();

    @MockBean
    TechMapRepository techMapRepository;

    @MockBean
    TechMapMapperImpl techMapMapper;

    @MockBean
    WorkerRepository workerRepository;

    @MockBean
    MaterialRepository materialRepository;

    TechMapService techMapService;

    @BeforeAll
    static void setUpAll() {
        testWorkerDTO1.setId(11);
        testWorkerDTO1.setName("testName1");
        testWorkerDTO1.setLastName("testLastName1");

        testWorkerDTO2.setId(12);
        testWorkerDTO2.setName("testName2");
        testWorkerDTO2.setLastName("testLastName2");

        testMaterialDTO1.setId(21);
        testMaterialDTO1.setName("testName1");

        testMaterialDTO2.setId(22);
        testMaterialDTO2.setName("testName2");

        testTechMapPostDTO1.setWorker(11);
        testTechMapPostDTO1.setMaterial(21);

        testTechMapDTO1.setId(31);
        testTechMapDTO1.setWorker(testWorkerDTO1);
        testTechMapDTO1.setMaterial(testMaterialDTO1);

        testTechMapDTO2.setId(32);
        testTechMapDTO2.setWorker(testWorkerDTO2);
        testTechMapDTO2.setMaterial(testMaterialDTO2);
    }

    @BeforeEach
    void setUp() {
        techMapService = new TechMapService(techMapMapper, techMapRepository, workerRepository, materialRepository);
    }

    @AfterEach
    void tearDown() {
        clearInvocations(techMapRepository);
    }

    @Test
    void add() {

        // testing to add testTechMap1
        when(workerRepository.existsById(testTechMapPostDTO1.getWorker())).thenReturn(true);
        when(materialRepository.existsById(testTechMapPostDTO1.getMaterial())).thenReturn(true);

        when(workerRepository.findById(testWorkerEntity1.getId())).thenReturn(testWorkerEntity1);
        when(materialRepository.findById(testMaterialEntity1.getId())).thenReturn(testMaterialEntity1);

        when(techMapRepository.save(any(TechMapEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // testing OK status
        assertEquals(TechMapService.STATUS_OK, techMapService.add(testTechMapPostDTO1));

        // testing NO_WORKER status
        when(workerRepository.existsById(any())).thenReturn(false);
        assertEquals(TechMapService.NO_WORKER, techMapService.add(testTechMapPostDTO1));

        // testing NO_MATERIAL status
        when(workerRepository.existsById(any())).thenReturn(true);
        when(materialRepository.existsById(any())).thenReturn(false);
        assertEquals(TechMapService.NO_MATERIAL, techMapService.add(testTechMapPostDTO1));

        verify(techMapRepository, times(1)).save(any(TechMapEntity.class));
    }

    @Test
    void getAll() {
        Set<TechMapGeneratedDTO> testListDTO = new HashSet<>();

        testListDTO.add(testTechMapDTO1);
        testListDTO.add(testTechMapDTO2);

        Set<TechMapEntity> testListEntity = new HashSet<>();

        testListEntity.add(testTechMapEntity1);
        testListEntity.add(testTechMapEntity2);

        when(techMapRepository.findAll()).thenReturn(testListEntity);
        when(techMapMapper.techMapsToTechMapsAllDto(testListEntity)).thenReturn(testListDTO);

        assertEquals(testListDTO, techMapService.getAll());
        verify(techMapRepository, times(1)).findAll();
    }

    @Test
    void getById() {

        int techMapId = 10;
        int wrongTechMapId = 11;

        when(techMapRepository.findById(techMapId)).thenReturn(testTechMapEntity1);
        when(techMapRepository.existsById(techMapId)).thenReturn(true);
        when(techMapMapper.techMapEntityToTechMapDto(testTechMapEntity1)).thenReturn(testTechMapDTO1);

        // test to find existing TechMapGeneratedDTO
        assertEquals(testTechMapDTO1, techMapService.getById(techMapId));
        // test if TechMapGeneratedDTO not exist
        assertNull(techMapService.getById(wrongTechMapId));

        verify(techMapRepository, times(1)).findById(techMapId);
    }

    @Test
    void updateById() {
        int newWorker = 101;
        int wrongNewWorker = 201;
        int newMaterial = 102;
        int wrongNewMaterial = 202;
        int techMapId = 31;
        int wrongTechMapId = 33;

        when(techMapRepository.existsById(techMapId)).thenReturn(true);
        when(techMapRepository.existsById(wrongTechMapId)).thenReturn(false);
        when(techMapRepository.findById(techMapId)).thenReturn(testTechMapEntity1);

        when(workerRepository.existsById(newWorker)).thenReturn(true);
        when(workerRepository.existsById(wrongNewWorker)).thenReturn(false);
        when(workerRepository.findById(newWorker)).thenReturn(testWorkerEntity1);

        when(materialRepository.existsById(newMaterial)).thenReturn(true);
        when(materialRepository.existsById(wrongNewMaterial)).thenReturn(false);
        when(materialRepository.findById(newMaterial)).thenReturn(testMaterialEntity1);

        when(techMapRepository.save(any(TechMapEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // test to update existing TechMapGeneratedDTO
        assertEquals(TechMapService.STATUS_OK,
                techMapService.updateById(techMapId, newWorker, newMaterial));
        // test to update existing TechMapGeneratedDTO. Change only worker
        assertEquals(TechMapService.STATUS_OK,
                techMapService.updateById(techMapId, newWorker, null));
        // test to update existing TechMapGeneratedDTO. Change only material
        assertEquals(TechMapService.STATUS_OK,
                techMapService.updateById(techMapId, null, newMaterial));

        // test if TechMapGeneratedDTO not exist
        assertEquals(TechMapService.NO_TECH_MAP,
                techMapService.updateById(wrongTechMapId, newWorker, newMaterial));
        // test if worker not exist
        assertEquals(TechMapService.NO_WORKER,
                techMapService.updateById(techMapId, wrongNewWorker, newMaterial));
        // test if material not exist
        assertEquals(TechMapService.NO_MATERIAL,
                techMapService.updateById(techMapId, newWorker, wrongNewMaterial));

        verify(techMapRepository, times(3)).save(any(TechMapEntity.class));
    }

    @Test
    void deleteById() {
        int techMapId = 10;
        int wrongTechMapId = 11;

        when(techMapRepository.existsById(techMapId)).thenReturn(true);
        when(techMapRepository.existsById(wrongTechMapId)).thenReturn(false);

        // test to update existing TechMapGeneratedDTO
        assertTrue(techMapService.deleteById(techMapId));

        assertFalse(techMapService.deleteById(wrongTechMapId));

        verify(techMapRepository, times(1)).deleteById(techMapId);
    }
}