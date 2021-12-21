package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.mappers.WorkersMapper;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.model.WorkerPostGeneratedDTO;
import ru.leonov.neotraining.repositories.WorkerRepository;

import java.util.Set;

@Service
public class WorkersService {

    private final WorkersMapper workersMapper;

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkersService(WorkerRepository workerRepository, WorkersMapper workersMapper) {
        this.workerRepository = workerRepository;
        this.workersMapper = workersMapper;
    }

    public boolean add(WorkerPostGeneratedDTO workerDto) {
        WorkerEntity workerEntity = workersMapper.workerPostDtoToWorkerEntity(workerDto);
        return workerRepository.save(workerEntity).equals(workerEntity);
    }

    public Set<WorkerGeneratedDTO> getAll() {
        return workersMapper.workersToWorkersAllDto(workerRepository.findAll());
    }

    public WorkerGeneratedDTO getById(int id) {
        if (workerRepository.existsById(id)) {
            return workersMapper.workerEntityToWorkerDto(workerRepository.findById(id));
        } else return null;
    }

    public boolean updateById(int id, String name, String lastName) {
        if (workerRepository.existsById(id)) {
            WorkerEntity worker = workerRepository.findById(id);
            if (name != null) worker.setName(name);
            if (lastName != null) worker.setLastName(lastName);
            return workerRepository.save(worker) == worker;
        } else return false;
    }

    public boolean deleteById(int id) {
        if (workerRepository.existsById(id)) {
            workerRepository.deleteById(id);
            return true;
        } else return false;
    }
}
