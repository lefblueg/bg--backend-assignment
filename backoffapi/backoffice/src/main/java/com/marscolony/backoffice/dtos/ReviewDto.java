package com.marscolony.backoffice.dtos;

import com.marscolony.backoffice.entities.Unit;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data

public class ReviewDto {

    private Long id;
    private String userId;
    private String description;
    @Min(value = 1, message = "minimum stars takes value 1")
    @Max(value = 5, message = "maximum stars takes value 5")
    private int stars;
    //private Unit unit;

}
