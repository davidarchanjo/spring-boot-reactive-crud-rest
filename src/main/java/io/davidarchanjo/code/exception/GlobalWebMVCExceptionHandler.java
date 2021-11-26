package io.davidarchanjo.code.exception;

import io.davidarchanjo.code.model.dto.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
@ResponseBody
public class GlobalWebMVCExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AppNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Mono<ErrorDTO> handlerAppNotFoundException(AppNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        final ErrorDTO error = ErrorDTO.builder()
            .code(ErrorIndicator.ERROR_RESOURCE_NOT_FOUND.getCode())
            .message(ErrorIndicator.ERROR_RESOURCE_NOT_FOUND.getMessage())
            .httpStatus(HttpStatus.NOT_FOUND)
            .build();

        return Mono.just(error);
    }

    @ExceptionHandler(AppAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Mono<ErrorDTO> handlerAppAlreadyExistException(AppAlreadyExistException ex) {
        log.error(ex.getMessage(), ex);

        final ErrorDTO error = ErrorDTO.builder()
            .code(ErrorIndicator.ERROR_RESOURCE_ALREADY_EXIST.getCode())
            .message(ErrorIndicator.ERROR_RESOURCE_ALREADY_EXIST.getMessage())
            .httpStatus(HttpStatus.CONFLICT)
            .build();

        return Mono.just(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorDTO> handlerWebExchangeBindException(WebExchangeBindException ex) {
        log.error(ex.getMessage(), ex);

        final List<String> details = ex.getAllErrors()
            .stream()
            .map(o -> messageSource.getMessage(
                "error.input.validation",
                new Object[]{
                        ((DefaultMessageSourceResolvable) Objects.requireNonNull(o.getArguments())[0]).getDefaultMessage(),
                        o.getDefaultMessage()
                },
                Locale.getDefault()))
            .collect(Collectors.toList());

        final ErrorDTO error = ErrorDTO.builder()
            .code(ErrorIndicator.ERROR_INPUT_VALIDATION.getCode())
            .message(ErrorIndicator.ERROR_INPUT_VALIDATION.getMessage())
            .httpStatus(HttpStatus.BAD_REQUEST)
            .details(details)
            .build();

        return Mono.just(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorDTO> handlerException(Exception ex) {
        log.error(ex.getMessage(), ex);

        final ErrorDTO error = ErrorDTO.builder()
            .code(ErrorIndicator.ERROR_INTERNAL_SERVER_FAILURE.getCode())
            .message(ErrorIndicator.ERROR_INTERNAL_SERVER_FAILURE.getMessage())
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();

        return Mono.just(error);
    }

}
