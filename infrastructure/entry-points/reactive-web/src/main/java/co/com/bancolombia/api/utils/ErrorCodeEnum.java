package co.com.bancolombia.api.utils;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    INVALID_FRANCHISE("INVALID_FRANCHISE", "The  entered is invalid"),
    FRANCHISE_NOT_FOUND("FRANCHISE_NOT_FOUND", "The franchise was not found"),
    FRANCHISE_ALREADY_EXISTS("FRANCHISE_ALREADY_EXISTS", "There is already a franchise with that name"),
    DATABASE_ERROR("DATABASE_ERROR", "Database error"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Unexpected server error");

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
