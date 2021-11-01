package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.services.WorkersService;

@Controller
@RequestMapping(value = "/worker")
@Api(tags = "Workers")
public class WorkersController {

    @Autowired
    private WorkersService workersService;

    //#######
    //  ADD
    //#######
    @ApiOperation(value = "Add a new worker.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Worker added successfully"),
            @ApiResponse(code = 400, message = "Parameters missing"),
            @ApiResponse(code = 500, message = "Worker not added!")
    })
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> addWorker(
            @ApiParam(value = "Name of worker.", required = true) @RequestParam String name,
            @ApiParam(value = "Last name of worker.", required = true) @RequestParam String lastName) {
        if (workersService.addWorker(name, lastName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //#########
    // GET ALL
    //#########
    @ApiOperation(value = "Get list of all workers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns list of all workers as JSON object.", response = WorkerEntity[].class),
            @ApiResponse(code = 204, message = "Returns empty list of no workers in database.")
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Iterable<WorkerEntity>> getAllWorkers() {
        Iterable<WorkerEntity> workersList = workersService.getAllWorkers();
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
    @ApiOperation(value = "Get specific worker with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns requested worker as JSON object.", response = WorkerEntity.class),
            @ApiResponse(code = 404, message = "Requested worker not found")
    })
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<WorkerEntity> getWorkerById(@ApiParam(value = "Id of requested worker.", required = true)
                                                      @PathVariable int id) {
        WorkerEntity worker = workersService.getById(id);
        if (worker != null) {
            return new ResponseEntity<>(worker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    //  UPDATE
    //#########
    @ApiOperation(value = "Change name and last name of worker with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requested worker updated successfully."),
            @ApiResponse(code = 404, message = "Requested worker not found.")
    })
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateWorkerById(
            @ApiParam(value = "Id of requested worker.", required = true)
            @PathVariable int id,
            @ApiParam(value = "New name of worker. Remains the same if empty.")
            @RequestParam(required = false) String name,
            @ApiParam(value = "New last name of worker. Remains the same if empty.")
            @RequestParam(required = false) String lastName) {
        if (workersService.updateById(id, name, lastName)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //#########
    // DELETE
    //#########
    @ApiOperation(value = "Delete worker with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requested worker deleted successfully."),
            @ApiResponse(code = 404, message = "Requested worker not found.")
    })
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteWorkerById(@ApiParam(value = "Id of requested worker.", required = true) @PathVariable int id) {
        if (workersService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
