package com.zerobase.storereserve.dto;

import com.zerobase.storereserve.domain.Store;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class StoreRegisterDto {

    @NotBlank
    @Size(max = 20)
    private String storename;

    @NotBlank
    private Double lat;

    @NotBlank
    private Double lng;

    @NotBlank
    private String description;

    public static StoreRegisterDto fromEntity(Store store) {
        return StoreRegisterDto.builder()
                .storename(store.getStorename())
                .lat(store.getLat())
                .lng(store.getLng())
                .description(store.getDescription())
                .build();
    }
}
