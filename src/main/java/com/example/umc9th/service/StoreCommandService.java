package com.example.umc9th.service;

import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.entity.Region;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.RegionRepository;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.exception.ErrorStatus;
import com.example.umc9th.global.exception.GeneralException;
import com.example.umc9th.web.dto.StoreRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

    public Store saveStore(Long regionId, StoreRequestDto.AddStoreDto request) {
        // 1. 지역 확인 (에러 처리는 앞서 배운대로 커스텀 에러 사용 권장)
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.REGION_NOT_FOUND));

        // 2. 가게 Entity 생성 및 저장
        Store store = StoreConverter.toStore(request, region);
        return storeRepository.save(store);
    }
}