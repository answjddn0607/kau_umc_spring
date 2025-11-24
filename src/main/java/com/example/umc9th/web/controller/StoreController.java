package com.example.umc9th.web.controller;

import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.global.common.ApiResponse;
import com.example.umc9th.global.common.SuccessStatus;
import com.example.umc9th.service.StoreCommandService;
import com.example.umc9th.web.dto.StoreRequestDto;
import com.example.umc9th.web.dto.StoreResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class StoreController {

    private final StoreCommandService storeCommandService;

    @PostMapping("/{regionId}/stores")
    public ApiResponse<StoreResponseDto.AddResponseDto> addStore(@PathVariable Long regionId,
                                                                 @RequestBody @Valid StoreRequestDto.AddStoreDto request) {
        Store store = storeCommandService.saveStore(regionId, request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED, StoreConverter.toAddResponseDto(store));
    }
}