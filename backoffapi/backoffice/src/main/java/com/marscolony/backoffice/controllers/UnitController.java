package com.marscolony.backoffice.controllers;

import com.marscolony.backoffice.dtos.UnitDto;
import com.marscolony.backoffice.dtos.UnitResponse;
import com.marscolony.backoffice.services.UnitService;
import com.marscolony.backoffice.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/units")
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService service) {
        this.unitService = service;
    }

    //create the unit
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UnitDto> createPost(@Valid @RequestBody UnitDto unitDto) {
        return new ResponseEntity<>(unitService.createUnit(unitDto), HttpStatus.CREATED);
    }

    @GetMapping
    public UnitResponse getAllUnits(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return unitService.getAllUnits(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getUnitById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(unitService.getUnitByIncomingId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UnitDto> updateUnit(@Valid @RequestBody UnitDto unitDto, @PathVariable(name="id") Long id) {
        var updateUnit = unitService.updateUnit(unitDto, id);
        return new ResponseEntity<>(updateUnit, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        unitService.deleteUnitById(id);
        return new ResponseEntity<>("Post entity deleted", HttpStatus.OK);
    }
}
