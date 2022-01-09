package com.marscolony.backoffice.dtos;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class UnitDto {

    private Long id;

    @NotEmpty(message = "Unit title must not be empty")
    @Size(min = 2, message = "Unit title should have at least two characters")
    private String title;

    @NotEmpty(message = "Unit region must not be empty")
    @Size(min = 2, message = "Unit region should have at least two characters")
    private String region;

    @NotEmpty(message = "Unit description must not be empty")
    @Size(min = 5, message = "Unit description should have at least five characters")
    private String description;

    private String cancellationPolicy;

    @NotEmpty(message = "provide a URL for the unit image")
    private String imageUrl;

    @DecimalMin(value = "1.0", inclusive = true, message = "price must be greater than 1.0")
    private BigDecimal price;

    private Set<ReviewDto> reviews;
}
