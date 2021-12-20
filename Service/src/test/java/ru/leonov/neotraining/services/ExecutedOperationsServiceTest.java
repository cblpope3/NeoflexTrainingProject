package ru.leonov.neotraining.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsDTO;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsPostDTO;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.dto.workers_dto.WorkerDTO;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.ExecutedOperationsMapperImpl;
import ru.leonov.neotraining.repositories.ExecutedOperationsRepository;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ExecutedOperationsServiceTest {

    private final WorkerEntity testWorkerEntity1 = new WorkerEntity(11, "testName1", "testLastName1");
    private final WorkerEntity testWorkerEntity2 = new WorkerEntity(12, "testName2", "testLastName2");
    private final WorkerDTO testWorkerDTO1 = new WorkerDTO(11, "testName1", "testLastName1");
    private final WorkerDTO testWorkerDTO2 = new WorkerDTO(12, "testName2", "testLastName2");

    private final MaterialEntity testMaterialEntity1 = new MaterialEntity(21, "testName1");
    private final MaterialEntity testMaterialEntity2 = new MaterialEntity(22, "testName2");
    private final MaterialDTO testMaterialDTO1 = new MaterialDTO(21, "testName1");
    private final MaterialDTO testMaterialDTO2 = new MaterialDTO(22, "testName2");

    private final TechMapEntity testTechMapEntity1 = new TechMapEntity(31, testWorkerEntity1, testMaterialEntity1);
    private final ExecutedOperationsEntity testExecutedOperationsEntity1 = new ExecutedOperationsEntity(testTechMapEntity1);
    private final TechMapEntity testTechMapEntity2 = new TechMapEntity(32, testWorkerEntity2, testMaterialEntity2);
    private final ExecutedOperationsEntity testExecutedOperationsEntity2 = new ExecutedOperationsEntity(testTechMapEntity2);
    private final TechMapDTO testTechMapDTO1 = new TechMapDTO(31, testWorkerDTO1, testMaterialDTO1);
    private final ExecutedOperationsDTO testExecutedOperationsDTO1 = new ExecutedOperationsDTO(41, testTechMapDTO1, "14-02-1990");
    private final TechMapDTO testTechMapDTO2 = new TechMapDTO(32, testWorkerDTO2, testMaterialDTO2);
    private final ExecutedOperationsDTO testExecutedOperationsDTO2 = new ExecutedOperationsDTO(42, testTechMapDTO2, "13-04-2000");
    private final ExecutedOperationsPostDTO testOperationPost = new ExecutedOperationsPostDTO(11, 21, 31);
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
        List<ExecutedOperationsDTO> testListDTO = new ArrayList<>();

        testListDTO.add(testExecutedOperationsDTO1);
        testListDTO.add(testExecutedOperationsDTO2);

        List<ExecutedOperationsEntity> testListEntity = new ArrayList<>();

        testListEntity.add(testExecutedOperationsEntity1);
        testListEntity.add(testExecutedOperationsEntity2);

        when(executedOperationsRepository.findAllByOrderByIdDesc()).thenReturn(testListEntity);
        when(executedOperationsMapper.executedOpsEntityToExecutedOpsDtoAll(testListEntity)).thenReturn(testListDTO);

        assertEquals(testListDTO, executedOperationsService.getAll());
        verify(executedOperationsRepository, times(1)).findAllByOrderByIdDesc();
    }

    @Test
    void getById() {

        int executedOperationsId = 10;
        int wrongExecutedOperationsId = 11;

        when(executedOperationsRepository.findById(executedOperationsId)).thenReturn(testExecutedOperationsEntity1);
        when(executedOperationsRepository.existsById(executedOperationsId)).thenReturn(true);
        when(executedOperationsMapper.executedOpsEntityToExecutedOpsDto(testExecutedOperationsEntity1)).thenReturn(testExecutedOperationsDTO1);

        // test to find existing executedOperationsDTO
        assertEquals(testExecutedOperationsDTO1, executedOperationsService.getById(executedOperationsId));
        // test if executedOperationsDTO not exist
        assertNull(executedOperationsService.getById(wrongExecutedOperationsId));

        verify(executedOperationsRepository, times(1)).findById(executedOperationsId);
    }

    @Test
    void deleteById() {
        int executedOperationsId = 10;
        int wrongExecutedOperationsId = 11;

        when(executedOperationsRepository.existsById(executedOperationsId)).thenReturn(true);
        when(executedOperationsRepository.existsById(wrongExecutedOperationsId)).thenReturn(false);

        // test to delete existing executedOperationsDTO
        assertTrue(executedOperationsService.deleteById(executedOperationsId));
        // test if executedOperationsDTO not exist
        assertFalse(executedOperationsService.deleteById(wrongExecutedOperationsId));

        verify(executedOperationsRepository, times(1)).deleteById(executedOperationsId);
    }
}