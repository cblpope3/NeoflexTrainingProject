package ru.leonov.neotraining.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.WorkersMapperImpl;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.model.WorkerPostGeneratedDTO;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WorkersServiceTest {

    private static final WorkerEntity testWorkerEntity1 = new WorkerEntity("testName1", "testLastName1");
    private static final WorkerEntity testWorkerEntity2 = new WorkerEntity("testName2", "testLastName2");
    private static final WorkerPostGeneratedDTO testWorkerPostDTO1 = new WorkerPostGeneratedDTO();
    private static final WorkerGeneratedDTO testWorkerDTO1 = new WorkerGeneratedDTO();
    private static final WorkerGeneratedDTO testWorkerDTO2 = new WorkerGeneratedDTO();
    @MockBean
    WorkerRepository workerRepository;
    @MockBean
    WorkersMapperImpl workersMapper;
    WorkersService workersService;

    @BeforeAll
    static void setUpAll() {
        testWorkerPostDTO1.setName("testName1");
        testWorkerPostDTO1.setLastName("testLastName1");

        testWorkerDTO1.setId(11);
        testWorkerDTO1.setName("testName1");
        testWorkerDTO1.setLastName("testLastName1");

        testWorkerDTO2.setId(12);
        testWorkerDTO2.setName("testName2");
        testWorkerDTO2.setLastName("testLastName2");
    }

    @BeforeEach
    void setUp() {
        workersService = new WorkersService(workerRepository, workersMapper);
    }

    @AfterEach
    void tearDown() {
        clearInvocations(workerRepository);
    }

    @Test
    void add() {
        when(workersMapper.workerPostDtoToWorkerEntity(any(WorkerPostGeneratedDTO.class))).thenReturn(testWorkerEntity1);
        when(workerRepository.save(any(WorkerEntity.class))).thenReturn(testWorkerEntity1);

        workersService.add(testWorkerPostDTO1);

        verify(workerRepository, times(1)).save(testWorkerEntity1);
    }

    @Test
    void getAll() {
        Set<WorkerGeneratedDTO> testListDTO = new HashSet<>();

        testListDTO.add(testWorkerDTO1);
        testListDTO.add(testWorkerDTO2);

        Set<WorkerEntity> testListEntity = new HashSet<>();

        testListEntity.add(testWorkerEntity1);
        testListEntity.add(testWorkerEntity2);

        when(workerRepository.findAll()).thenReturn(testListEntity);
        when(workersMapper.workersToWorkersAllDto(testListEntity)).thenReturn(testListDTO);

        assertEquals(testListDTO, workersService.getAll());
        verify(workerRepository, times(1)).findAll();
    }

    @Test
    void getById() {

        when(workerRepository.findById(10)).thenReturn(testWorkerEntity1);
        when(workerRepository.existsById(10)).thenReturn(true);
        when(workersMapper.workerEntityToWorkerDto(testWorkerEntity1)).thenReturn(testWorkerDTO1);

        // test to find existing workersDTO
        assertEquals(testWorkerDTO1, workersService.getById(10));
        // test if workersDTO not exist
        assertNull(workersService.getById(11));

        verify(workerRepository, times(1)).findById(10);
    }

    @Test
    void updateByIdNormal() {
        String newName = testWorkerDTO2.getName();
        String newLastName = testWorkerDTO2.getLastName();
        int workerId = 10;
        int wrongWorkerId = 11;

        when(workerRepository.findById(workerId)).thenReturn(testWorkerEntity1);
        when(workerRepository.existsById(workerId)).thenReturn(true);
        when(workerRepository.existsById(wrongWorkerId)).thenReturn(false);
        when(workerRepository.save(testWorkerEntity1)).thenReturn(testWorkerEntity1);
        when(workerRepository.save(testWorkerEntity2)).thenReturn(testWorkerEntity2);

        // test to update existing workersDTO
        assertTrue(workersService.updateById(workerId, newName, newLastName));
        // test if workersDTO not exist
        assertFalse(workersService.updateById(wrongWorkerId, newName, newLastName));

        verify(workerRepository, times(1)).save(testWorkerEntity1);
    }

    @Test
    void updateByIdPartial() {
        String newName = testWorkerDTO2.getName();
        String newLastName = testWorkerDTO2.getLastName();
        int workerId = 10;
        int wrongWorkerId = 11;

        when(workerRepository.findById(workerId)).thenReturn(testWorkerEntity1);
        when(workerRepository.existsById(workerId)).thenReturn(true);
        when(workerRepository.existsById(wrongWorkerId)).thenReturn(false);
        when(workerRepository.save(testWorkerEntity1)).thenReturn(testWorkerEntity1);
        when(workerRepository.save(testWorkerEntity2)).thenReturn(testWorkerEntity2);

        // test to update existing workersDTO
        assertTrue(workersService.updateById(workerId, newName, null));
        assertTrue(workersService.updateById(workerId, null, newLastName));

        verify(workerRepository, times(2)).save(testWorkerEntity1);
    }

    @Test
    void deleteById() {
        int workerId = 10;
        int wrongWorkerId = 11;

        when(workerRepository.existsById(workerId)).thenReturn(true);
        when(workerRepository.existsById(wrongWorkerId)).thenReturn(false);

        // test to update existing workersDTO
        assertTrue(workersService.deleteById(workerId));

        assertFalse(workersService.deleteById(wrongWorkerId));

        verify(workerRepository, times(1)).deleteById(workerId);
    }
}