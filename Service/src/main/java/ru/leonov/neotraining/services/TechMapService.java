package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.TechMapMapper;
import ru.leonov.neotraining.model.TechMapGeneratedDTO;
import ru.leonov.neotraining.model.TechMapPostGeneratedDTO;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.Set;

@Service
public class TechMapService {

    //Variables used by methods 'add' and 'updateById'
    public static final int STATUS_OK = 10;
    public static final int NO_TECH_MAP = 11;
    public static final int NO_WORKER = 12;
    public static final int NO_MATERIAL = 13;
    public static final int NOT_SAVED = 14;

    private final TechMapMapper techMapMapper;

    private final TechMapRepository techMapRepository;

    private final WorkerRepository workerRepository;

    private final MaterialRepository materialRepository;

    @Autowired
    public TechMapService(TechMapMapper techMapMapper, TechMapRepository techMapRepository, WorkerRepository workerRepository, MaterialRepository materialRepository) {
        this.techMapMapper = techMapMapper;
        this.techMapRepository = techMapRepository;
        this.workerRepository = workerRepository;
        this.materialRepository = materialRepository;
    }

    /**
     * Method returns int with status code:
     * '10' if techMap created successfully,
     * '12' if worker not found in database,
     * '13' if material not found in database,
     * '14' if techMap not saved properly.
     */
    public int add(TechMapPostGeneratedDTO newTechMap) {
        int workerId = newTechMap.getWorker();
        int materialId = newTechMap.getMaterial();

        //check if worker and material are present in database
        if (!workerRepository.existsById(workerId)) return NO_WORKER;
        if (!materialRepository.existsById(materialId)) return NO_MATERIAL;

        WorkerEntity worker = workerRepository.findById(workerId);
        MaterialEntity material = materialRepository.findById(materialId);

        TechMapEntity techMap = new TechMapEntity(worker, material);

        boolean isSaved = techMapRepository.save(techMap).equals(techMap);

        if (isSaved) return STATUS_OK;
        else return NOT_SAVED;
    }

    public Set<TechMapGeneratedDTO> getAll() {
        return techMapMapper.techMapsToTechMapsAllDto(techMapRepository.findAll());
    }

    public TechMapGeneratedDTO getById(int id) {
        if (techMapRepository.existsById(id)) {
            return techMapMapper.techMapEntityToTechMapDto(techMapRepository.findById(id));
        } else return null;
    }

    /**
     * Method returns int with status code:
     * '10' if techMap updated successfully,
     * '11' if techMap not found in database,
     * '12' if new worker not found in database,
     * '13' if new material not found in database,
     * '14' if techMap not saved properly.
     */
    public int updateById(int id, Integer workerId, Integer materialId) {

        // check if techMap with given id exist in database
        if (!techMapRepository.existsById(id)) return NO_TECH_MAP;
        TechMapEntity techMap = techMapRepository.findById(id);

        // if we're going to change worker
        if (workerId != null) {
            // check if new worker exist in database
            if (!workerRepository.existsById(workerId)) return NO_WORKER;
            else techMap.setWorker(workerRepository.findById(workerId.intValue()));
        }

        // if we're going to change material
        if (materialId != null) {
            // check if new material exist in database
            if (!materialRepository.existsById(materialId)) return NO_MATERIAL;
            else techMap.setMaterial(materialRepository.findById(materialId.intValue()));
        }
        if (techMapRepository.save(techMap).equals(techMap)) return STATUS_OK;
        else return NOT_SAVED;
    }

    public boolean deleteById(int id) {
        if (techMapRepository.existsById(id)) {
            techMapRepository.deleteById(id);
            return true;
        } else return false;
    }
}