package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.services.MaterialService;

@Controller
@RequestMapping(value = "/material")
@Api(tags = "Materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    //#########
    //   ADD
    //#########
    @ApiOperation(value = "Add a new material.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Material added successfully."),
            @ApiResponse(code = 400, message = "Parameters missing."),
            @ApiResponse(code = 500, message = "Material not added.")
    })
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> add(
            @ApiParam(value = "Name of material.", required = true) @RequestParam String name) {
        if (materialService.add(name)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //#########
    // GET ALL
    //#########
    @ApiOperation(value = "Get list of all materials.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns list of all materials as JSON object.", response = MaterialEntity[].class),
            @ApiResponse(code = 204, message = "Returns empty list if no materials in database.")
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Iterable<MaterialEntity>> getAll() {
        Iterable<MaterialEntity> materialList = materialService.getAll();
        if (materialList.iterator().hasNext()) {
            return new ResponseEntity<>(materialList, HttpStatus.OK);
        } else {
            //list of materials is empty
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //#########
    // GET ONE
    //#########
    @ApiOperation(value = "Get specific material with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns requested material as JSON object.", response = MaterialEntity.class),
            @ApiResponse(code = 404, message = "Requested material not found.")
    })
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MaterialEntity> getById(
            @ApiParam(value = "Id of requested material.", required = true) @PathVariable int id) {
        MaterialEntity material = materialService.getById(id);
        if (material != null) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    // UPDATE
    //#########
    @ApiOperation(value = "Change name of material with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requested material updated successfully."),
            @ApiResponse(code = 404, message = "Requested material not found.")
    })
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateById(
            @ApiParam(value = "Id of requested material.", required = true) @PathVariable int id,
            @ApiParam(value = "New name of material. Remains the same if empty.") @RequestParam(required = false) String name) {
        if (materialService.updateById(id, name)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //#########
    // DELETE
    //#########
    @ApiOperation(value = "Delete material with id={id}.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requested material deleted successfully."),
            @ApiResponse(code = 404, message = "Requested material not found.")
    })
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@ApiParam(value = "Id of requested material.", required = true) @PathVariable int id) {
        if (materialService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
