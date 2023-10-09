package com.zerobase.storereserve.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class ReviewDto {

    private Long reviewId;

    @NotBlank
    @Size(min = 10)
    private String contents;

    @Min(1)
    @Max(5)
    private Integer rating;
}
