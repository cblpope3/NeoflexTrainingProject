package ru.leonov.neotraining.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapPostDTO;
import ru.leonov.neotraining.dto.workers_dto.WorkerDTO;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.TechMapMapperImpl;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TechMapServiceTest {

    private final WorkerEntity testWorkerEntity1 = new WorkerEntity(11, "testName1", "testLastName1");
    private final WorkerEntity testWorkerEntity2 = new WorkerEntity(12, "testName2", "testLastName2");
    private final WorkerDTO testWorkerDTO1 = new WorkerDTO(11, "testName1", "testLastName1");
    private final WorkerDTO testWorkerDTO2 = new WorkerDTO(12, "testName2", "testLastName2");

    private final MaterialEntity testMaterialEntity1 = new MaterialEntity(21, "testName1");
    private final MaterialEntity testMaterialEntity2 = new MaterialEntity(22, "testName2");
    private final MaterialDTO testMaterialDTO1 = new MaterialDTO(21, "testName1");
    private final MaterialDTO testMaterialDTO2 = new MaterialDTO(22, "testName2");

    private final TechMapEntity testTechMapEntity1 = new TechMapEntity(31, testWorkerEntity1, testMaterialEntity1);
    private final TechMapEntity testTechMapEntity2 = new TechMapEntity(32, testWorkerEntity2, testMaterialEntity2);
    private final TechMapPostDTO testTechMapPostDTO1 = new TechMapPostDTO(11, 21);
    private final TechMapDTO testTechMapDTO1 = new TechMapDTO(31, testWorkerDTO1, testMaterialDTO1);
    private final TechMapDTO testTechMapDTO2 = new TechMapDTO(32, testWorkerDTO2, testMaterialDTO2);

    @MockBean
    TechMapRepository techMapRepository;

    @MockBean
    TechMapMapperImpl techMapMapper;

    @MockBean
    WorkerRepository workerRepository;

    @MockBean
    MaterialRepository materialRepository;

    TechMapService techMapService;

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
        when(workerRepository.existsById(testTechMapPostDTO1.getWorkerId())).thenReturn(true);
        when(materialRepository.existsById(testTechMapPostDTO1.getMaterialId())).thenReturn(true);

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
        List<TechMapDTO> testListDTO = new ArrayList<>();

        testListDTO.add(testTechMapDTO1);
        testListDTO.add(testTechMapDTO2);

        List<TechMapEntity> testListEntity = new ArrayList<>();

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

        // test to find existing techMapDTO
        assertEquals(testTechMapDTO1, techMapService.getById(techMapId));
        // test if techMapDTO not exist
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

        // test to update existing techMapDTO
        assertEquals(TechMapService.STATUS_OK,
                techMapService.updateById(techMapId, String.valueOf(newWorker), String.valueOf(newMaterial)));
        // test to update existing techMapDTO. Change only worker
        assertEquals(TechMapService.STATUS_OK,
                techMapService.updateById(techMapId, String.valueOf(newWorker), null));
        // test to update existing techMapDTO. Change only material
        assertEquals(TechMapService.STATUS_OK,
                techMapService.updateById(techMapId, null, String.valueOf(newMaterial)));

        // test if techMapDTO not exist
        assertEquals(TechMapService.NO_TECH_MAP,
                techMapService.updateById(wrongTechMapId, String.valueOf(newWorker), String.valueOf(newMaterial)));
        // test if worker not exist
        assertEquals(TechMapService.NO_WORKER,
                techMapService.updateById(techMapId, String.valueOf(wrongNewWorker), String.valueOf(newMaterial)));
        // test if material not exist
        assertEquals(TechMapService.NO_MATERIAL,
                techMapService.updateById(techMapId, String.valueOf(newWorker), String.valueOf(wrongNewMaterial)));

        verify(techMapRepository, times(3)).save(any(TechMapEntity.class));
    }

    @Test
    void deleteById() {
        int techMapId = 10;
        int wrongTechMapId = 11;

        when(techMapRepository.existsById(techMapId)).thenReturn(true);
        when(techMapRepository.existsById(wrongTechMapId)).thenReturn(false);

        // test to update existing techMapDTO
        assertTrue(techMapService.deleteById(techMapId));

        assertFalse(techMapService.deleteById(wrongTechMapId));

        verify(techMapRepository, times(1)).deleteById(techMapId);
    }
}