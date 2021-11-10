package ru.leonov.neotraining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsPostDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.services.ExecutedOperationsService;
import ru.leonov.neotraining.services.TechMapService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomOperationsExecutor {

    @Autowired
    ExecutedOperationsService operationsService;

    @Autowired
    TechMapService techMapService;

    @Scheduled(cron = "0 */10 10-12 * * *", zone = "Europe/Moscow")
    public void executeOperation() {

        //getting list of all tech maps
        List<TechMapDTO> techMapList = new ArrayList<>();
        techMapService.getAll().forEach(techMapList::add);

        //generating list of random techMaps
        List<Integer> randomIndexList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            randomIndexList.add(ThreadLocalRandom.current().nextInt(0, techMapList.size()));
        }

        //executing 5 operations from randomIndexList
        for (int randomIndex : randomIndexList) {

            //getting techMap from list
            TechMapDTO techMap = techMapList.get(randomIndex);

            //getting technical map, worker and material id
            int techMapId = techMap.getId();
            int workerId = techMap.getWorker().getId();
            int materialId = techMap.getMaterial().getId();

            ExecutedOperationsPostDTO newOperation = new ExecutedOperationsPostDTO();
            newOperation.setTechMapId(techMapId);
            newOperation.setWorkerId(workerId);
            newOperation.setMaterialId(materialId);

            operationsService.executeOperation(newOperation);
        }
    }
}
