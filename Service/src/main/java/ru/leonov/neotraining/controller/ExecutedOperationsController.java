package ru.leonov.neotraining.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.api.ExecutedOperationsApi;
import ru.leonov.neotraining.model.ExecutedOperationGeneratedDTO;
import ru.leonov.neotraining.model.ExecutedOperationPostGeneratedDTO;
import ru.leonov.neotraining.services.ExecutedOperationsService;

import java.util.List;
import java.util.Set;

@Controller
@Api(tags = {"Executed operations"})
@RequestMapping(value = "operations")
public class ExecutedOperationsController implements ExecutedOperationsApi {

    @Autowired
    private ExecutedOperationsService executedOpsService;

    //#########
    // REPORT
    //#########
    @GetMapping("/ui")
    public String getReport(Model model) {
        List<ExecutedOperationGeneratedDTO> operationsList = executedOpsService.getAllOrdered();
        model.addAttribute("operations", operationsList);
        return "operations_report";
    }

    //Uncomment this controller to get ability of manual operation execution
    //#######
    //  ADD
    //#######
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> addOperation(@RequestBody ExecutedOperationPostGeneratedDTO request) {

        return switch (executedOpsService.executeOperation(request)) {
            case ExecutedOperationsService.STATUS_OK -> new ResponseEntity<>(HttpStatus.OK);
            case ExecutedOperationsService.NO_TECH_MAP -> new ResponseEntity<>("Technical map not found in database.", HttpStatus.NOT_FOUND);
            case ExecutedOperationsService.NO_WORKER -> new ResponseEntity<>("Worker not found in database.", HttpStatus.NOT_FOUND);
            case ExecutedOperationsService.NO_MATERIAL -> new ResponseEntity<>("Material not found in database.", HttpStatus.NOT_FOUND);
            case ExecutedOperationsService.WORKER_NOT_MATCH -> new ResponseEntity<>("Worker don't match technical map.", HttpStatus.UNPROCESSABLE_ENTITY);
            case ExecutedOperationsService.MATERIAL_NOT_MATCH -> new ResponseEntity<>("Material don't match technical map.", HttpStatus.UNPROCESSABLE_ENTITY);
            case ExecutedOperationsService.NOT_SAVED -> new ResponseEntity<>("Executed operation haven't been saved.", HttpStatus.INTERNAL_SERVER_ERROR);
            default -> new ResponseEntity<>("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    //#########
    // GET ALL
    //#########
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Set<ExecutedOperationGeneratedDTO>> getOperationsList() {
        Set<ExecutedOperationGeneratedDTO> operationsList = executedOpsService.getAll();
        if (operationsList.iterator().hasNext()) {
            return new ResponseEntity<>(operationsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //#########
    // GET ONE
    //#########
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ExecutedOperationGeneratedDTO> getOperationById(@PathVariable int id) {
        ExecutedOperationGeneratedDTO operation = executedOpsService.getById(id);
        if (operation != null) {
            return new ResponseEntity<>(operation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    // DELETE
    //#########
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteOperationById(@PathVariable int id) {
        if (executedOpsService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
