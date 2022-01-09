package com.marscolony.backoffice.services;

import com.marscolony.backoffice.dtos.UnitDto;
import com.marscolony.backoffice.dtos.UnitResponse;

import java.util.List;

public interface UnitService {
    UnitDto createUnit(UnitDto unitDto);
    UnitResponse getAllUnits(int pageNo, int pageSize, String sortBy, String sortDir);
    UnitDto getUnitByIncomingId(Long id);
    UnitDto updateUnit(UnitDto unitDto, Long id);
    void deleteUnitById(Long id);

}
