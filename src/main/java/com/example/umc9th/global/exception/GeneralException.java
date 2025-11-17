package com.example.umc9th.global.exception;

import com.example.umc9th.global.common.BaseCode;
import com.example.umc9th.global.common.ReasonDTO;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseCode code;

    public GeneralException(BaseCode code) {
        super(code.getReasonHttpStatus().getMessage());
        this.code = code;
    }

    public ReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}