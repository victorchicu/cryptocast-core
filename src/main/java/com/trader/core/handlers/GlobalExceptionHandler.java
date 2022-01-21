package com.trader.core.handlers;

import com.trader.core.exceptions.SymbolNotFoundException;
import com.trader.core.users.exceptions.EmailNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({EmailNotFoundException.class})
    public ResponseEntity<Object> emailNotFoundException(EmailNotFoundException ex, WebRequest webRequest) {
        LOGGER.warn("Request description: {} | Error message: {}", webRequest.getDescription(true), ex.getMessage());
        return new ResponseEntity<>(
                new ErrorDto(
                        Collections.singletonList(new ErrorDto.Details("EmailNotFoundException", null, ex.getMessage()))
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({SymbolNotFoundException.class})
    public ResponseEntity<Object> handleSymbolNotFoundException(SymbolNotFoundException ex, WebRequest webRequest) {
        LOGGER.warn("Request description: {} | Error message: {}", webRequest.getDescription(true), ex.getMessage());
        return new ResponseEntity<>(
                new ErrorDto(
                        Collections.singletonList(new ErrorDto.Details("SymbolNotFoundException", null, ex.getMessage()))
                ),
                HttpStatus.NOT_FOUND
        );
    }

    public static class ErrorDto {
        private final List<Details> errors;

        public ErrorDto(List<Details> errors) {
            this.errors = errors;
        }

        public List<Details> getErrors() {
            return errors;
        }

        public static final class Details {
            private final String code;
            private final String field;
            private final String description;

            public Details(String code, String field, String description) {
                this.code = code;
                this.field = field;
                this.description = description;
            }

            public String getCode() {
                return code;
            }

            public String getField() {
                return field;
            }

            public String getDescription() {
                return description;
            }
        }
    }
}
