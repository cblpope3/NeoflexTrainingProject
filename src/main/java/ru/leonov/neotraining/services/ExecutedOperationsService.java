package ru.leonov.neotraining.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsDTO;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsPostDTO;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.mappers.ExecutedOperationsMapper;
import ru.leonov.neotraining.repositories.ExecutedOperationsRepository;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

@Service
public class ExecutedOperationsService {

    //Variables used by method 'executeOperation'
    public static final int STATUS_OK = 0;
    public static final int NO_TECH_MAP = 1;
    public static final int NO_WORKER = 2;
    public static final int NO_MATERIAL = 3;
    public static final int WORKER_NOT_MATCH = 4;
    public static final int MATERIAL_NOT_MATCH = 5;
    public static final int NOT_SAVED = 6;

    private static final Logger log = Logger.getLogger(ExecutedOperationsService.class);

    @Autowired
    private ExecutedOperationsMapper executedOperationsMapper;

    @Autowired
    private ExecutedOperationsRepository executedOperationsRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private TechMapRepository techMapRepository;

    /**
     * Method returns int with status code:
     * '0' if operation executed successfully,
     * '1' if techMap not found in database,
     * '2' if worker not found in database,
     * '3' if material not found in database,
     * '4' if worker not match techMap,
     * '5' if material not match techMap,
     * '6' if techMap not saved properly.
     */
    public int executeOperation(ExecutedOperationsPostDTO request) {
        int techMapId = request.getTechMapId();
        int workerId = request.getWorkerId();
        int materialId = request.getMaterialId();

        //check if worker, material and technical map exist in database
        if (!techMapRepository.existsById(techMapId)) {
            log.warn(String.format("Executing operation failed! Technical map '#%d' doesn't exist.", techMapId));
            return NO_TECH_MAP;
        }
        if (!workerRepository.existsById(workerId)) {
            log.warn(String.format("Executing operation failed! Worker '#%d' doesn't exist.", workerId));
            return NO_WORKER;
        }
        if (!materialRepository.existsById(materialId)) {
            log.warn(String.format("Executing operation failed! Material '#%d' doesn't exist.", materialId));
            return NO_MATERIAL;
        }

        TechMapEntity executingTechMap = techMapRepository.findById(techMapId);

        //check if worker and material are in current technical map
        if (executingTechMap.getWorker().getId() != workerId) {
            log.warn(String.format("Executing operation failed! Worker '#%d' is not in technical map '#%d'.", workerId, techMapId));
            return WORKER_NOT_MATCH;
        }
        if (executingTechMap.getMaterial().getId() != materialId) {
            log.warn(String.format("Executing operation failed! Material '#%d' is not in technical map '#%d'.", materialId, techMapId));
            return MATERIAL_NOT_MATCH;
        }

        ExecutedOperationsEntity operation = new ExecutedOperationsEntity(executingTechMap);
        if (executedOperationsRepository.save(operation) == operation) {
            log.info(String.format("Operation '#%d' executed.", operation.getId()));
            return STATUS_OK;
        } else {
            log.error("Unknown error!");
            return NOT_SAVED;
        }
    }

    public Iterable<ExecutedOperationsDTO> getAll() {
        return executedOperationsMapper.executedOpsEntityToExecutedOpsDtoAll(executedOperationsRepository.findAll());
    }

    public ExecutedOperationsDTO getById(int id) {
        if (executedOperationsRepository.existsById(id)) {
            return executedOperationsMapper.executedOpsEntityToExecutedOpsDto(executedOperationsRepository.findById(id));
        } else return null;
    }

    public boolean deleteById(int id) {
        if (executedOperationsRepository.existsById(id)) {
            executedOperationsRepository.deleteById(id);
            return true;
        } else return false;
    }
}
