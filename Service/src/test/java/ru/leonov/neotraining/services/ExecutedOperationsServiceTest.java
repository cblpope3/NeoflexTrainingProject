package ru.leonov.neotraining.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.ExecutedOperationsMapperImpl;
import ru.leonov.neotraining.model.*;
import ru.leonov.neotraining.repositories.ExecutedOperationsRepository;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ExecutedOperationsServiceTest {

    private static final WorkerEntity testWorkerEntity1 = new WorkerEntity(11, "testName1", "testLastName1");
    private static final WorkerEntity testWorkerEntity2 = new WorkerEntity(12, "testName2", "testLastName2");
    private static final WorkerGeneratedDTO testWorkerDTO1 = new WorkerGeneratedDTO();
    private static final WorkerGeneratedDTO testWorkerDTO2 = new WorkerGeneratedDTO();

    private static final MaterialEntity testMaterialEntity1 = new MaterialEntity(21, "testName1");
    private static final MaterialEntity testMaterialEntity2 = new MaterialEntity(22, "testName2");
    private static final MaterialGeneratedDTO testMaterialDTO1 = new MaterialGeneratedDTO();
    private static final MaterialGeneratedDTO testMaterialDTO2 = new MaterialGeneratedDTO();

    private static final TechMapEntity testTechMapEntity1 = new TechMapEntity(31, testWorkerEntity1, testMaterialEntity1);
    private static final ExecutedOperationsEntity testExecutedOperationsEntity1 = new ExecutedOperationsEntity(testTechMapEntity1);
    private static final TechMapEntity testTechMapEntity2 = new TechMapEntity(32, testWorkerEntity2, testMaterialEntity2);
    private static final ExecutedOperationsEntity testExecutedOperationsEntity2 = new ExecutedOperationsEntity(testTechMapEntity2);
    private static final TechMapGeneratedDTO testTechMapDTO1 = new TechMapGeneratedDTO();
    private static final ExecutedOperationGeneratedDTO testExecutedOperationsDTO1 = new ExecutedOperationGeneratedDTO();
    private static final TechMapGeneratedDTO testTechMapDTO2 = new TechMapGeneratedDTO();
    private static final ExecutedOperationGeneratedDTO testExecutedOperationsDTO2 = new ExecutedOperationGeneratedDTO();
    private static final ExecutedOperationPostGeneratedDTO testOperationPost = new ExecutedOperationPostGeneratedDTO();
    @MockBean
    ExecutedOperationsRepository executedOperationsRepository;

    @MockBean
    ExecutedOperationsMapperImpl executedOperationsMapper;

    @MockBean
    TechMapRepository techMapRepository;

    @MockBean
    WorkerRepository workerRepository;

    @MockBean
    MaterialRepository materialRepository;

    ExecutedOperationsService executedOperationsService;

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

        testTechMapDTO1.setId(31);
        testTechMapDTO1.setWorker(testWorkerDTO1);
        testTechMapDTO1.setMaterial(testMaterialDTO1);

        testExecutedOperationsDTO1.setId(41);
        testExecutedOperationsDTO1.setTechMap(testTechMapDTO1);
        testExecutedOperationsDTO1.setDate("14-02-1990");

        testTechMapDTO2.setId(32);
        testTechMapDTO2.setWorker(testWorkerDTO2);
        testTechMapDTO2.setMaterial(testMaterialDTO2);

        testExecutedOperationsDTO2.setId(42);
        testExecutedOperationsDTO2.setTechMap(testTechMapDTO2);
        testExecutedOperationsDTO2.setDate("13-04-2000");


        testOperationPost.setWorkerId(11);
        testOperationPost.setMaterialId(21);
        testOperationPost.setTechMapId(31);
    }

    @BeforeEach
    void setUp() {
        executedOperationsService = new ExecutedOperationsService(
                executedOperationsMapper,
                executedOperationsRepository,
                workerRepository,
                materialRepository,
                techMapRepository);
    }

    @AfterEach
    void tearDown() {
        clearInvocations(executedOperationsRepository);
    }

    @Test
    void executeOperation() {
        int techMapId = testTechMapDTO1.getId();
        int wrongTechMapId = testTechMapDTO1.getId() + 100;
        int workerId = testWorkerDTO1.getId();
        int wrongWorkerId = testWorkerDTO1.getId() + 100;
        int materialId = testMaterialDTO1.getId();
        int wrongMaterialId = testMaterialDTO1.getId() + 100;

        when(techMapRepository.existsById(techMapId)).thenReturn(true);
        when(techMapRepository.existsById(wrongTechMapId)).thenReturn(false);
        when(workerRepository.existsById(workerId)).thenReturn(true);
        when(workerRepository.existsById(wrongWorkerId)).thenReturn(false);
        when(materialRepository.existsById(materialId)).thenReturn(true);
        when(materialRepository.existsById(wrongMaterialId)).thenReturn(false);

        when(techMapRepository.findById(techMapId)).thenReturn(testTechMapEntity1);

        when(executedOperationsRepository.save(any(ExecutedOperationsEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // testing OK status
        assertEquals(ExecutedOperationsService.STATUS_OK, executedOperationsService.executeOperation(testOperationPost));

        // testing NO_MATERIAL status
        testOperationPost.setMaterialId(wrongMaterialId);
        assertEquals(ExecutedOperationsService.NO_MATERIAL, executedOperationsService.executeOperation(testOperationPost));

        // testing NO_WORKER status
        testOperationPost.setWorkerId(wrongWorkerId);
        assertEquals(ExecutedOperationsService.NO_WORKER, executedOperationsService.executeOperation(testOperationPost));

        // testing NO_TECH_MAP status
        testOperationPost.setTechMapId(wrongTechMapId);
        assertEquals(ExecutedOperationsService.NO_TECH_MAP, executedOperationsService.executeOperation(testOperationPost));

        // testing WORKER_NOT_MATCH status
        when(techMapRepository.existsById(any())).thenReturn(true);
        when(workerRepository.existsById(any())).thenReturn(true);
        when(materialRepository.existsById(any())).thenReturn(true);
        testOperationPost.setTechMapId(techMapId);
        testOperationPost.setWorkerId(workerId);
        testOperationPost.setMaterialId(materialId);
        when(techMapRepository.findById(techMapId)).thenReturn(testTechMapEntity2);
        assertEquals(ExecutedOperationsService.WORKER_NOT_MATCH, executedOperationsService.executeOperation(testOperationPost));

        // testing MATERIAL_NOT_MATCH status
        testOperationPost.setWorkerId(testWorkerEntity2.getId());
        assertEquals(ExecutedOperationsService.MATERIAL_NOT_MATCH, executedOperationsService.executeOperation(testOperationPost));

        verify(executedOperationsRepository, times(1)).save(any(ExecutedOperationsEntity.class));
    }

    @Test
    void getAll() {
        Set<ExecutedOperationGeneratedDTO> testListDTO = new HashSet<>();

        testListDTO.add(testExecutedOperationsDTO1);
        testListDTO.add(testExecutedOperationsDTO2);

        Set<ExecutedOperationsEntity> testListEntity = new HashSet<>();

        testListEntity.add(testExecutedOperationsEntity1);
        testListEntity.add(testExecutedOperationsEntity2);

        when(executedOperationsRepository.findAll()).thenReturn(testListEntity);
        when(executedOperationsMapper.executedOpsEntityToExecutedOpsDtoAll(testListEntity)).thenReturn(testListDTO);

        assertEquals(testListDTO, executedOperationsService.getAll());
        verify(executedOperationsRepository, times(1)).findAll();
    }

    @Test
    void getAllOrdered() {
        List<ExecutedOperationGeneratedDTO> testListDTO = new ArrayList<>();

        testListDTO.add(testExecutedOperationsDTO1);
        testListDTO.add(testExecutedOperationsDTO2);

        List<ExecutedOperationsEntity> testListEntity = new ArrayList<>();

        testListEntity.add(testExecutedOperationsEntity1);
        testListEntity.add(testExecutedOperationsEntity2);

        when(executedOperationsRepository.findAllByOrderByIdDesc()).thenReturn(testListEntity);
        when(executedOperationsMapper.executedOpsEntityToExecutedOpsDtoAllOrdered(testListEntity)).thenReturn(testListDTO);

        assertEquals(testListDTO, executedOperationsService.getAllOrdered());
        verify(executedOperationsRepository, times(1)).findAllByOrderByIdDesc();
    }


    @Test
    void getById() {

        int executedOperationsId = 10;
        int wrongExecutedOperationsId = 11;

        when(executedOperationsRepository.findById(executedOperationsId)).thenReturn(testExecutedOperationsEntity1);
        when(executedOperationsRepository.existsById(executedOperationsId)).thenReturn(true);
        when(executedOperationsMapper.executedOpsEntityToExecutedOpsDto(testExecutedOperationsEntity1)).thenReturn(testExecutedOperationsDTO1);

        // test to find existing ExecutedOperationGeneratedDTO
        assertEquals(testExecutedOperationsDTO1, executedOperationsService.getById(executedOperationsId));
        // test if ExecutedOperationGeneratedDTO not exist
        assertNull(executedOperationsService.getById(wrongExecutedOperationsId));

        verify(executedOperationsRepository, times(1)).findById(executedOperationsId);
    }

    @Test
    void deleteById() {
        int executedOperationsId = 10;
        int wrongExecutedOperationsId = 11;

        when(executedOperationsRepository.existsById(executedOperationsId)).thenReturn(true);
        when(executedOperationsRepository.existsById(wrongExecutedOperationsId)).thenReturn(false);

        // test to delete existing ExecutedOperationGeneratedDTO
        assertTrue(executedOperationsService.deleteById(executedOperationsId));
        // test if ExecutedOperationGeneratedDTO not exist
        assertFalse(executedOperationsService.deleteById(wrongExecutedOperationsId));

        verify(executedOperationsRepository, times(1)).deleteById(executedOperationsId);
    }
}