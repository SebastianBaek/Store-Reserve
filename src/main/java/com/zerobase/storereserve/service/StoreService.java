package com.zerobase.storereserve.service;

import com.zerobase.storereserve.domain.Store;
import com.zerobase.storereserve.domain.User;
import com.zerobase.storereserve.dto.StoreInfoDto;
import com.zerobase.storereserve.dto.StoreRegisterDto;
import com.zerobase.storereserve.exception.impl.StoresNotFoundException;
import com.zerobase.storereserve.exception.impl.UserNotFoundException;
import com.zerobase.storereserve.repository.StoreRepository;
import com.zerobase.storereserve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public StoreRegisterDto storeRegister(
            StoreRegisterDto storeRegisterDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        return StoreRegisterDto.fromEntity(storeRepository.save(Store
                .builder()
                .storename(storeRegisterDto.getStorename())
                .lat(storeRegisterDto.getLat())
                .lng(storeRegisterDto.getLng())
                .description(storeRegisterDto.getDescription())
                .rating(0.0)
                .user(user)
                .build()));
    }

    public List<StoreInfoDto> getStoreWithName(String storename) {
        List<Store> stores = storeRepository.findByStorenameContaining(storename)
                .orElseThrow(() -> new StoresNotFoundException());

        return stores.stream().map(store -> StoreInfoDto.builder()
                .storeId(store.getId())
                .storename(store.getStorename())
                .lat(store.getLat())
                .lng(store.getLng())
                .description(store.getDescription())
                .rating(store.getRating())
                .build())
                .collect(Collectors.toList());
    }

    public List<StoreInfoDto> getAllStoreByABC() {
        List<Store> stores = storeRepository.findAllByOrderByStorenameAsc()
                .orElseThrow(() -> new StoresNotFoundException());

        return stores.stream().map(store -> StoreInfoDto.builder()
                        .storeId(store.getId())
                        .storename(store.getStorename())
                        .lat(store.getLat())
                        .lng(store.getLng())
                        .description(store.getDescription())
                        .rating(store.getRating())
                        .build())
                .collect(Collectors.toList());
    }

    public List<StoreInfoDto> getAllStoreByDistance(Double lat, Double lng) {
        List<Store> stores = storeRepository.findAllOrderByDistanceAsc(lat, lng)
                .orElseThrow(() -> new StoresNotFoundException());

        return stores.stream().map(store -> StoreInfoDto.builder()
                        .storeId(store.getId())
                        .storename(store.getStorename())
                        .lat(store.getLat())
                        .lng(store.getLng())
                        .description(store.getDescription())
                        .rating(store.getRating())
                        .build())
                .collect(Collectors.toList());
    }

    public List<StoreInfoDto> getAllStoreByRating() {
        List<Store> stores = storeRepository.findAllByOrderByRatingDesc()
                .orElseThrow(() -> new StoresNotFoundException());

        return stores.stream().map(store -> StoreInfoDto.builder()
                        .storeId(store.getId())
                        .storename(store.getStorename())
                        .lat(store.getLat())
                        .lng(store.getLng())
                        .description(store.getDescription())
                        .rating(store.getRating())
                        .build())
                .collect(Collectors.toList());
    }


}
