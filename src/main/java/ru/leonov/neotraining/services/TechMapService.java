package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

@Service
public class TechMapService {

    //Variables used by methods 'add' and 'updateById'
    public static final int STATUS_OK = 0;
    public static final int NO_TECH_MAP = 1;
    public static final int NO_WORKER = 2;
    public static final int NO_MATERIAL = 3;
    public static final int NOT_SAVED = 4;

    @Autowired
    private TechMapRepository techMapRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private MaterialRepository materialRepository;

    /**
     * Method returns int with status code:
     * '0' if techMap created successfully,
     * '2' if worker not found in database,
     * '3' if material not found in database,
     * '4' if techMap not saved properly.
     */
    public int add(int workerId, int materialId) {
        //check if worker and material are present in database
        if (!workerRepository.existsById(workerId)) return NO_WORKER;
        if (!materialRepository.existsById(materialId)) return NO_MATERIAL;

        TechMapEntity techMap = new TechMapEntity(workerRepository.findById(workerId),
                materialRepository.findById(materialId));
        if (techMapRepository.save(techMap) == techMap) return STATUS_OK;
        else return NOT_SAVED;
    }

    public Iterable<TechMapEntity> getAll() {
        return techMapRepository.findAll();
    }

    public TechMapEntity getById(int id) {
        if (techMapRepository.existsById(id)) return techMapRepository.findById(id);
        else return null;
    }

    /**
     * Method returns int with status code:
     * '0' if techMap updated successfully,
     * '1' if techMap not found in database,
     * '2' if new worker not found in database,
     * '3' if new material not found in database,
     * '4' if techMap not saved properly.
     */
    public int updateById(int id, String workerId, String materialId) {

        if (techMapRepository.existsById(id)) {
            // techMap with given id have been found in database
            TechMapEntity techMap = techMapRepository.findById(id);
            if (workerId != null) {
                if (!workerRepository.existsById(Integer.parseInt(workerId))) {
                    // worker not found in database
                    return NO_WORKER;
                } else {
                    // new worker id applied
                    techMap.setWorker(workerRepository.findById(Integer.parseInt(workerId)));
                }
            }
            if (materialId != null) {
                if (!materialRepository.existsById(Integer.parseInt(materialId))) {
                    // material not found in database
                    return NO_MATERIAL;
                } else {
                    // new worker id applied
                    techMap.setMaterial(materialRepository.findById(Integer.parseInt(materialId)));
                }
            }
            if (techMapRepository.save(techMap) == techMap) return STATUS_OK;
            else return NOT_SAVED;
        } else {
            // techMap with given id haven't been found in database
            return NO_TECH_MAP;
        }
    }

    public boolean deleteById(int id) {
        if (techMapRepository.existsById(id)) {
            techMapRepository.deleteById(id);
            return true;
        } else return false;
    }
}
