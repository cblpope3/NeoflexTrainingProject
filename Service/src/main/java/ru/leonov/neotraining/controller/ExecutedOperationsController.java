package ru.leonov.neotraining.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsDTO;
import ru.leonov.neotraining.services.ExecutedOperationsService;

@Controller
@RequestMapping(value = "operations")
@Api(tags = "Executed operations")
public class ExecutedOperationsController {

    @Autowired
    private ExecutedOperationsService executedOpsService;

    //#########
    // REPORT
    //#########
    @ApiOperation(value = "Get executed operations report.")
    @GetMapping("/ui")
    public String getReport(Model model) {
        Iterable<ExecutedOperationsDTO> operationsList = executedOpsService.getAll();
        model.addAttribute("operations", operationsList);
        return "operations_report";
    }

    //Uncomment this controller to get ability of manual operation execution
    //#######
    //  ADD
    //#######
//    @ApiOperation(value = "Try to execute an operation.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Operation executed successfully."),
//            @ApiResponse(code = 400, message = "Parameters missing."),
//            @ApiResponse(code = 404, message = "Several parameters not found in database. Read message in response body."),
//            @ApiResponse(code = 422, message = "Worker or material don't match technical map. Read message in response body."),
//            @ApiResponse(code = 500, message = "Internal server error. Read message in response body.")
//    })
//    @PostMapping("")
//    @ResponseBody
//    public ResponseEntity<String> add(
//            @ApiParam(value = "JSON object that contains information about operation to be executed.", required = true)
//            @RequestBody ExecutedOperationsPostDTO request) {
//
//        switch (executedOpsService.executeOperation(request)) {
//            case ExecutedOperationsService.STATUS_OK:
//                return new ResponseEntity<>(HttpStatus.OK);
//            case ExecutedOperationsService.NO_TECH_MAP:
//                return new ResponseEntity<>("Technical map not found in database.", HttpStatus.NOT_FOUND);
//            case ExecutedOperationsService.NO_WORKER:
//                return new ResponseEntity<>("Worker not found in database.", HttpStatus.NOT_FOUND);
//            case ExecutedOperationsService.NO_MATERIAL:
//                return new ResponseEntity<>("Material not found in database.", HttpStatus.NOT_FOUND);
//            case ExecutedOperationsService.WORKER_NOT_MATCH:
//                return new ResponseEntity<>("Worker don't match technical map.", HttpStatus.UNPROCESSABLE_ENTITY);
//            case ExecutedOperationsService.MATERIAL_NOT_MATCH:
//                return new ResponseEntity<>("Material don't match technical map.", HttpStatus.UNPROCESSABLE_ENTITY);
//            case ExecutedOperationsService.NOT_SAVED:
//                return new ResponseEntity<>("Executed operation haven't been saved.", HttpStatus.INTERNAL_SERVER_ERROR);
//            default:
//                return new ResponseEntity<>("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //#########
    // GET ALL
    //#########
    @ApiOperation(value = "Get list of all executed operations.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns list of all executed operations as JSON object.", response = ExecutedOperationsDTO[].class),
            @ApiResponse(code = 204, message = "Returns empty list if no executed operations in database.")
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Iterable<ExecutedOperationsDTO>> getAll() {
        Iterable<ExecutedOperationsDTO> operationsList = executedOpsService.getAll();
        if (operationsList.iterator().hasNext()) {
            return new ResponseEntity<>(operationsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //#########
    // GET ONE
    //#########
//    @ApiOperation(value = "Get specific operation with id={id}.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Returns requested executed operation as JSON object.", response = ExecutedOperationsDTO.class),
//            @ApiResponse(code = 404, message = "Requested operation not found.")
//    })
//    @GetMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<ExecutedOperationsDTO> getById(
//            @ApiParam(value = "Id of requested executed operation.", required = true) @PathVariable int id) {
//        ExecutedOperationsDTO operation = executedOpsService.getById(id);
//        if (operation != null) {
//            return new ResponseEntity<>(operation, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    //#########
    // DELETE
    //#########
//    @ApiOperation(value = "Delete executed operation with id={id}.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Requested operation deleted successfully."),
//            @ApiResponse(code = 404, message = "Requested operation not found.")
//    })
//    @DeleteMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<String> deleteById(
//            @ApiParam(value = "Id of requested executed operation.", required = true) @PathVariable int id) {
//        if (executedOpsService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
//        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
