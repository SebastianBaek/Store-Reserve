package com.zerobase.storereserve.dto;

import com.zerobase.storereserve.domain.Store;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreInfoDto {

    private Long storeId;

    private String storename;

    private Double lat;

    private Double lng;

    private String description;

    private Double rating;

    public static StoreInfoDto fromEntity(Store store) {
        return StoreInfoDto.builder()
                .storeId(store.getId())
                .storename(store.getStorename())
                .lat(store.getLat())
                .lng(store.getLng())
                .description(store.getDescription())
                .rating(store.getRating())
                .build();
    }
}
