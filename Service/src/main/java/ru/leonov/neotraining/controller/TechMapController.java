package ru.leonov.neotraining.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.api.TechnicalMapApi;
import ru.leonov.neotraining.model.TechMapGeneratedDTO;
import ru.leonov.neotraining.model.TechMapPostGeneratedDTO;
import ru.leonov.neotraining.services.TechMapService;

import java.util.Set;

@Controller
@Api(tags = {"Technical Map"})
@RequestMapping(value = "tech_map")
public class TechMapController implements TechnicalMapApi {

    @Autowired
    private TechMapService techMapService;

    //#######
    //  ADD
    //#######
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Object> addTechMap(@RequestBody TechMapPostGeneratedDTO techMap) {
        int savingStatus = techMapService.add(techMap);
        return switch (savingStatus) {
            case TechMapService.STATUS_OK -> new ResponseEntity<>(HttpStatus.OK);
            case TechMapService.NO_WORKER -> new ResponseEntity<>("Worker not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NO_MATERIAL -> new ResponseEntity<>("Material not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NOT_SAVED -> new ResponseEntity<>("Technical map not saved.", HttpStatus.INTERNAL_SERVER_ERROR);
            default -> new ResponseEntity<>("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    //#########
    // GET ALL
    //#########
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Set<TechMapGeneratedDTO>> getTechMapList() {
        Set<TechMapGeneratedDTO> techMapList = techMapService.getAll();
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
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<TechMapGeneratedDTO> getTechMapById(@PathVariable Integer id) {
        TechMapGeneratedDTO techMap = techMapService.getById(id);
        if (techMap != null) {
            return new ResponseEntity<>(techMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    //  UPDATE
    //#########
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> updateTechMapById(@PathVariable Integer id,
                                                    @RequestParam(required = false) Integer workerId,
                                                    @RequestParam(required = false) Integer materialId) {
        int updateResult = techMapService.updateById(id, workerId, materialId);
        return switch (updateResult) {
            case TechMapService.STATUS_OK ->
                    // updated properly
                    new ResponseEntity<>(HttpStatus.OK);
            case TechMapService.NO_TECH_MAP ->
                    // techMap not found in database
                    new ResponseEntity<>("Requested technical map not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NO_WORKER ->
                    // new worker not found in database
                    new ResponseEntity<>("New worker not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NO_MATERIAL ->
                    // new material not found in database
                    new ResponseEntity<>("New material not found in database.", HttpStatus.NOT_FOUND);
            case TechMapService.NOT_SAVED ->
                    // techMap not saved properly
                    new ResponseEntity<>("Technical map has not saved.", HttpStatus.INTERNAL_SERVER_ERROR);
            default -> new ResponseEntity<>("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    //#########
    // DELETE
    //#########
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTechMapById(@PathVariable Integer id) {
        if (techMapService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
