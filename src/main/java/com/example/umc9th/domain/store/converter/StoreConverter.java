package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.entity.Region;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.web.dto.StoreRequestDto;
import com.example.umc9th.web.dto.StoreResponseDto;

import java.time.LocalDateTime;

public class StoreConverter {

    public static StoreResponseDto.AddResponseDto toAddResponseDto(Store store) {
        return StoreResponseDto.AddResponseDto.builder()
                .storeId(store.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreRequestDto.AddStoreDto request, Region region) {
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .region(region)
                .build();
    }
}