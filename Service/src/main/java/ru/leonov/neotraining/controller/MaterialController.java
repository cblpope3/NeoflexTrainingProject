package ru.leonov.neotraining.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.api.MaterialApi;
import ru.leonov.neotraining.model.MaterialGeneratedDTO;
import ru.leonov.neotraining.model.MaterialPostGeneratedDTO;
import ru.leonov.neotraining.services.MaterialService;

import java.util.Set;

@Controller
@Api(tags = {"Material"})
@RequestMapping(value = "/material")
public class MaterialController implements MaterialApi {

    @Autowired
    private MaterialService materialService;

    //#########
    //   ADD
    //#########
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Void> addMaterial(@RequestBody MaterialPostGeneratedDTO material) {
        if (material.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (materialService.add(material)) {
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
    public ResponseEntity<Set<MaterialGeneratedDTO>> getMaterialList() {
        Set<MaterialGeneratedDTO> materialList = materialService.getAll();
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
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MaterialGeneratedDTO> getMaterialById(@PathVariable Integer id) {
        MaterialGeneratedDTO material = materialService.getById(id);
        if (material != null) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //#########
    // UPDATE
    //#########
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> updateMaterialById(@PathVariable Integer id,
                                                   @RequestParam(required = false) String name) {
        if (materialService.updateById(id, name)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //#########
    // DELETE
    //#########
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteMaterialById(@PathVariable Integer id) {
        if (materialService.deleteById(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
