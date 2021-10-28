package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.repositories.WorkerRepository;

@Service
public class WorkersService {

    @Autowired
    private WorkerRepository workerRepository;

    public void addWorker(String name, String lastName){
        WorkerEntity worker = new WorkerEntity(name, lastName);

        workerRepository.save(worker);
    }

    public Iterable<WorkerEntity> getAllWorkers(){
        return workerRepository.findAll();
    }

    //TODO implement methods: getById, deleteById, updateById
}
