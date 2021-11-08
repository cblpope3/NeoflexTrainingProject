package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapPostDTO;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.mappers.TechMapMapper;
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
    private TechMapMapper techMapMapper;

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
    public int add(TechMapPostDTO newTechMap) {
        int workerId = newTechMap.getWorkerId();
        int materialId = newTechMap.getMaterialId();

        //check if worker and material are present in database
        if (!workerRepository.existsById(workerId)) return NO_WORKER;
        if (!materialRepository.existsById(materialId)) return NO_MATERIAL;

        TechMapEntity techMap = new TechMapEntity(workerRepository.findById(workerId),
                materialRepository.findById(materialId));
        if (techMapRepository.save(techMap) == techMap) return STATUS_OK;
        else return NOT_SAVED;
    }

    public Iterable<TechMapDTO> getAll() {
        return techMapMapper.techMapsToTechMapsAllDto(techMapRepository.findAll());
    }

    public TechMapDTO getById(int id) {
        if (techMapRepository.existsById(id)) {
            return techMapMapper.techMapEntityToTechMapDto(techMapRepository.findById(id));
        } else return null;
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

        // check if techMap with given id exist in database
        if (!techMapRepository.existsById(id)) return NO_TECH_MAP;
        TechMapEntity techMap = techMapRepository.findById(id);

        // if we're going to change worker
        if (workerId != null) {
            // check if new worker exist in database
            if (!workerRepository.existsById(Integer.parseInt(workerId))) return NO_WORKER;
            else techMap.setWorker(workerRepository.findById(Integer.parseInt(workerId)));
        }

        // if we're going to change material
        if (materialId != null) {
            // check if new material exist in database
            if (!materialRepository.existsById(Integer.parseInt(materialId))) return NO_MATERIAL;
            else techMap.setMaterial(materialRepository.findById(Integer.parseInt(materialId)));
        }
        if (techMapRepository.save(techMap) == techMap) return STATUS_OK;
        else return NOT_SAVED;
    }

    public boolean deleteById(int id) {
        if (techMapRepository.existsById(id)) {
            techMapRepository.deleteById(id);
            return true;
        } else return false;
    }
}
