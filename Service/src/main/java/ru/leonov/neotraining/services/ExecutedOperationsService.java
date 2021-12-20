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
    public static final int STATUS_OK = 10;
    public static final int NO_TECH_MAP = 11;
    public static final int NO_WORKER = 12;
    public static final int NO_MATERIAL = 13;
    public static final int WORKER_NOT_MATCH = 14;
    public static final int MATERIAL_NOT_MATCH = 15;
    public static final int NOT_SAVED = 16;

    private static final Logger log = Logger.getLogger(ExecutedOperationsService.class);

    private final ExecutedOperationsMapper executedOperationsMapper;

    private final ExecutedOperationsRepository executedOperationsRepository;

    private final WorkerRepository workerRepository;

    private final MaterialRepository materialRepository;

    private final TechMapRepository techMapRepository;

    @Autowired
    public ExecutedOperationsService(ExecutedOperationsMapper executedOperationsMapper,
                                     ExecutedOperationsRepository executedOperationsRepository,
                                     WorkerRepository workerRepository,
                                     MaterialRepository materialRepository,
                                     TechMapRepository techMapRepository) {
        this.executedOperationsMapper = executedOperationsMapper;
        this.executedOperationsRepository = executedOperationsRepository;
        this.workerRepository = workerRepository;
        this.materialRepository = materialRepository;
        this.techMapRepository = techMapRepository;
    }

    /**
     * Method returns int with status code:
     * '10' if operation executed successfully,
     * '11' if techMap not found in database,
     * '12' if worker not found in database,
     * '13' if material not found in database,
     * '14' if worker not match techMap,
     * '15' if material not match techMap,
     * '16' if techMap not saved properly.
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
        if (executedOperationsRepository.save(operation).equals(operation)) {
            log.info(String.format("Operation '#%d' executed.", operation.getId()));
            return STATUS_OK;
        } else {
            log.error("Unknown error!");
            return NOT_SAVED;
        }
    }

    public Iterable<ExecutedOperationsDTO> getAll() {
        return executedOperationsMapper.executedOpsEntityToExecutedOpsDtoAll(executedOperationsRepository.findAllByOrderByIdDesc());
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
