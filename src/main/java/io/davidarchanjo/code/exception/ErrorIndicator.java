package io.davidarchanjo.code.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorIndicator {

    ERROR_RESOURCE_NOT_FOUND("001", "Resource not found"),
    ERROR_RESOURCE_ALREADY_EXIST("002", "Resource already exist"),
    ERROR_INPUT_VALIDATION("003", "Input validation failed"),
    ERROR_INTERNAL_SERVER_FAILURE("004", "Internal server failure");

    private String code;
    private String message;

}
