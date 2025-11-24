package com.example.umc9th.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class StoreRequestDto {

    @Getter
    public static class AddStoreDto {
        @NotBlank
        private String name;
        @NotBlank
        private String address;
    }
}