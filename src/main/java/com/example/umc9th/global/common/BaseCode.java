package com.example.umc9th.global.common;

// 성공/실패 응답 DTO를 위한 인터페이스
public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}