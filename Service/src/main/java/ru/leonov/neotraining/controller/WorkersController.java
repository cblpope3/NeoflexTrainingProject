package ru.leonov.neotraining.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.api.WorkerApi;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.model.WorkerPostGeneratedDTO;
import ru.leonov.neotraining.services.WorkersService;

import java.util.Set;

@Controller
@Api(tags = {"Worker"})
@RequestMapping(value = "/worker")
public class WorkersController implements WorkerApi {

    @Autowired
    private WorkersService workersService;

    //#######
    //  ADD
    //#######
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Void> addWorker(@RequestBody WorkerPostGeneratedDTO worker) {
        if (worker.getName() == null || worker.getLastName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (workersService.add(worker)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //#########
    // GET ALL
    //#########
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Set<WorkerGeneratedDTO>> getWorkersList() {
        Set<WorkerGeneratedDTO> workersList = workersService.getAll();
        if (workersList.iterator().hasNext()) {
            return new ResponseEntity<>(workersList, HttpStatus.OK);
        } else {
            //list of workers is empty
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //#########
    // GET ONE
    //#########
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<WorkerGeneratedDTO> getWorkerById(@PathVariable Integer id) {
        WorkerGeneratedDTO worker = workersService.getById(id);
        if (worker != null) {
            return new ResponseEntity<>(worker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    //  UPDATE
    //#########
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> updateWorkerById(@PathVariable Integer id,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String lastName) {
        if (workersService.updateById(id, name, lastName)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //#########
    // DELETE
    //#########
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteWorkerById(@PathVariable Integer id) {
        if (workersService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
