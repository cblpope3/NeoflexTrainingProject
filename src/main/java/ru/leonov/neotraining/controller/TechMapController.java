package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapPostDTO;
import ru.leonov.neotraining.services.TechMapService;

@Controller
@RequestMapping(value = "tech_map")
@Api(tags = "Technical maps")
public class TechMapController {

    @Autowired
    private TechMapService techMapService;

    //#######
    //  ADD
    //#######
    @ApiOperation(value = "Add a new technical map.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Technical map added successfully."),
            @ApiResponse(code = 400, message = "Parameters missing."),
            @ApiResponse(code = 404, message = "Worker or material not found in database. Read message in response body."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> add(
            @ApiParam(value = "New technical map as JSON object.", required = true) @RequestBody TechMapPostDTO techMap) {
        int savingStatus = techMapService.add(techMap);
        switch (savingStatus) {
            case TechMapService.STATUS_OK:
                return new ResponseEntity<>(HttpStatus.OK);
            case TechMapService.NO_WORKER:
                return new ResponseEntity<>("Worker not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NO_MATERIAL:
                return new ResponseEntity<>("Material not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NOT_SAVED:
                return new ResponseEntity<>("Technical map not saved.", HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return new ResponseEntity<>("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //#########
    // GET ALL
    //#########
    @ApiOperation(value = "Get list of all technical maps.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns list of all technical maps as JSON object.", response = TechMapDTO[].class),
            @ApiResponse(code = 204, message = "Returns empty list if no technical maps in database.")
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Iterable<TechMapDTO>> getAll() {
        Iterable<TechMapDTO> techMapList = techMapService.getAll();
        if (techMapList.iterator().hasNext()) {
            return new ResponseEntity<>(techMapList, HttpStatus.OK);
        } else {
            //list of tech maps is empty
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //#########
    // GET ONE
    //#########
    @ApiOperation(value = "Get specific technical map with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns requested technical map as JSON object.", response = TechMapDTO.class),
            @ApiResponse(code = 404, message = "Requested technical map not found.")
    })
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<TechMapDTO> getById(
            @ApiParam(value = "Id of requested technical map.", required = true) @PathVariable int id) {
        TechMapDTO techMap = techMapService.getById(id);
        if (techMap != null) {
            return new ResponseEntity<>(techMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    //  UPDATE
    //#########
    @ApiOperation(value = "Change existing technical map with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requested technical map updated successfully."),
            @ApiResponse(code = 404, message = "Several parameters not found in database. Read message in response body.", response = String.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = String.class)
    })
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateById(
            @ApiParam(value = "Id of requested technical map.", required = true)
            @PathVariable int id,
            @ApiParam(value = "New id of worker associated with technical map. Remains the same if empty.")
            @RequestParam(required = false) String workerId,
            @ApiParam(value = "New id of material associated with technical map. Remains the same if empty.")
            @RequestParam(required = false) String materialId) {
        int updateResult = techMapService.updateById(id, workerId, materialId);
        switch (updateResult) {
            case TechMapService.STATUS_OK:
                // updated properly
                return new ResponseEntity<>(HttpStatus.OK);
            case TechMapService.NO_TECH_MAP:
                // techMap not found in database
                return new ResponseEntity<>("Requested technical map not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NO_WORKER:
                // new worker not found in database
                return new ResponseEntity<>("New worker not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NO_MATERIAL:
                // new material not found in database
                return new ResponseEntity<>("New material not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NOT_SAVED:
                // techMap not saved properly
                return new ResponseEntity<>("Technical map has not saved.", HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return new ResponseEntity<>("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //#########
    // DELETE
    //#########
    @ApiOperation(value = "Delete technical map with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requested technical map deleted successfully."),
            @ApiResponse(code = 404, message = "Requested technical map not found.")
    })
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(
            @ApiParam(value = "Id of requested technical map.", required = true) @PathVariable int id) {
        if (techMapService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
