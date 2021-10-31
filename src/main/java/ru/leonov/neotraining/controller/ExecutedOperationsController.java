package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.data_containers.ExecutedOperationsJSONContainer;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.services.ExecutedOperationsService;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "operations")
@Api
public class ExecutedOperationsController {

    @Autowired
    private ExecutedOperationsService executedOpsService;

    //TODO decide if this function has to be removed
//    @GetMapping("/add")
//    @ResponseBody
//    public String addOld(@RequestParam int workerId, @RequestParam int materialId, @RequestParam int techMapId){
//        if (executedOpsService.executeOperation(workerId, materialId, techMapId)) {
//            //if operation executed successfully
//            return String.format("Operation with worker '#%d', material '#%d' and techMap '#%d' was executed successfully!",
//                    workerId, materialId, techMapId);
//        } else return "operation execution failed!";
//    }

    @ApiOperation(value = "Try to execute an operation.")
    @PostMapping("/add")
    @ResponseBody
    public String add(@ApiParam(value = "JSON object that contains information about operation to be executed.",
                                required = true)
                          @RequestBody ExecutedOperationsJSONContainer request){
        int workerId = request.getWorkerId();
        int materialId = request.getMaterialId();
        int techMapId = request.getTechMapId();
        if (executedOpsService.executeOperation(workerId, materialId, techMapId)) {
            //if operation executed successfully
            return String.format("Operation with worker '#%d', material '#%d' and techMap '#%d' was executed successfully!",
                    workerId, materialId, techMapId);
        } else return "operation execution failed!";
    }

    @ApiOperation(value = "Get list of all executed operations.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns list of all executed operations as JSON object.",
            response = ExecutedOperationsEntity[].class)})
    @GetMapping("")
    @ResponseBody
    public Iterable<ExecutedOperationsEntity> getAll(){
        return executedOpsService.getAll();
    }

    @ApiOperation(value = "Get specific operation with id={id}.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns requested executed operation as JSON object.",
            response = ExecutedOperationsEntity.class)})
    @GetMapping("/{id}")
    @ResponseBody
    public ExecutedOperationsEntity getById(@ApiParam(value = "Id of requested executed operation.", required = true)
                                                @PathVariable int id){
        return executedOpsService.getById(id);
    }

    @ApiOperation(value = "Delete executed operation with id={id}.")
    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteById(@ApiParam(value = "Id of requested executed operation.", required = true)
                                 @PathVariable int id){
        if (executedOpsService.deleteById(id)) return "deleted successfully!";
        else return String.format("operation '#%s' not found.", id);
    }
}
