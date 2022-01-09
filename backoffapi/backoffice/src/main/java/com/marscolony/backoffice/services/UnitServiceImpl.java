package com.marscolony.backoffice.services;

import com.marscolony.backoffice.dtos.UnitDto;
import com.marscolony.backoffice.dtos.UnitResponse;
import com.marscolony.backoffice.entities.Unit;
import com.marscolony.backoffice.exceptions.ResourceNotFoundException;
import com.marscolony.backoffice.repositories.UnitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    private UnitRepository unitRepository;

    private ModelMapper mapper;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository, ModelMapper mapper) {

        this.unitRepository = unitRepository;
        this.mapper = mapper;
    }

    @Override
    public UnitDto createUnit(UnitDto incomingUnitDto) {

        var unit = mapToEntity(incomingUnitDto);
        var savedUnit = unitRepository.save(unit);
        var unitResponse = mapToDTO(savedUnit);
        return unitResponse;

    }

    @Override
    public UnitResponse getAllUnits(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        var pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Unit> units = unitRepository.findAll(pageable);
        List<Unit> listOfUnits = units.getContent();
        List<UnitDto> content = listOfUnits.stream()
                .map(unit -> mapToDTO(unit))
                .collect(Collectors.toList());

        UnitResponse unitResponse = new UnitResponse();
        unitResponse.setContent(content);
        unitResponse.setPageNo(units.getNumber());
        unitResponse.setPageSize(units.getSize());
        unitResponse.setTotalElements(units.getTotalElements());
        unitResponse.setTotalPages(units.getTotalPages());
        unitResponse.setLast(units.isLast());

        return unitResponse;
    }

    @Override
    public UnitDto getUnitByIncomingId(Long id) {
        var unit = unitRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Unit", "id", id));
        return mapToDTO(unit);

    }

    @Override
    public UnitDto updateUnit(UnitDto unitDto, Long id) {
        var unit = unitRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Unit", "id", id));
        unit.setTitle(unitDto.getTitle());
        unit.setRegion(unitDto.getRegion());
        unit.setDescription(unitDto.getDescription());
        unit.setCancellationPolicy(unitDto.getCancellationPolicy());
        unit.setImageUrl(unitDto.getImageUrl());
        unit.setPrice(unitDto.getPrice());

        var updatedUnit = unitRepository.save(unit);
        return mapToDTO(updatedUnit);

    }

    @Override
    public void deleteUnitById(Long id) {
        var unit = unitRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Unit", "id", id));
        unitRepository.delete(unit);
    }

    private UnitDto mapToDTO(Unit unit) {

        UnitDto unitDto = mapper.map(unit, UnitDto.class);
        return unitDto;

        // var res = new UnitDto();
        // res.setId(newUnit.getId());
        // res.setTitle(newUnit.getTitle());
        // res.setRegion(newUnit.getRegion());
        // res.setDescription(newUnit.getDescription());
        // res.setCancellationPolicy(newUnit.getCancellationPolicy());
        // res.setImageUrl(newUnit.getImageUrl());
        // res.setPrice(newUnit.getPrice());
        // return res;
    }

    private Unit mapToEntity(UnitDto unitDto) {

        Unit unit = mapper.map(unitDto, Unit.class);
        // var unit = new Unit();
        // unit.setTitle(unitDto.getTitle());
        // unit.setRegion(unitDto.getRegion());
        // unit.setDescription(unitDto.getDescription());
        // unit.setCancellationPolicy(unitDto.getCancellationPolicy());
        // unit.setImageUrl(unitDto.getImageUrl());
        // unit.setPrice(unitDto.getPrice());
        return unit;
    }
}
