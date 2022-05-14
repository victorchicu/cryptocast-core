package com.coinbank.core.handlers;

import com.binance.api.client.exception.BinanceApiException;
import com.coinbank.core.exceptions.AssetTrackerNotFoundException;
import com.coinbank.core.exceptions.NotFoundEmailException;
import com.coinbank.core.exceptions.SymbolNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final boolean DEFAULT_INCLUDE_CLIENT_INFO = true;

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BinanceApiException.class})
    public ResponseEntity<Object> handleBinanceApiException(BinanceApiException ex, WebRequest webRequest) {
        LOG.warn("Binance error {}", ex.getError());
        LOG.warn("Error message {}", ex.getMessage());
        LOG.warn("Request description {}", webRequest.getDescription(DEFAULT_INCLUDE_CLIENT_INFO));
        return new ResponseEntity<>(
                new ErrorDto(
                        Collections.singletonList(
                                new ErrorDto.Details(BinanceApiException.class.getSimpleName(), null, ex.getMessage())
                        )
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({NotFoundEmailException.class})
    public ResponseEntity<Object> handleNotFoundEmailException(NotFoundEmailException ex, WebRequest webRequest) {
        LOG.warn("Error message {}", ex.getMessage());
        LOG.warn("Request description {}", webRequest.getDescription(DEFAULT_INCLUDE_CLIENT_INFO));
        return new ResponseEntity<>(
                new ErrorDto(
                        Collections.singletonList(
                                new ErrorDto.Details("EmailNotFoundException", null, ex.getMessage())
                        )
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({SymbolNotFoundException.class})
    public ResponseEntity<Object> handleSymbolNotFoundException(SymbolNotFoundException ex, WebRequest webRequest) {
        LOG.warn("Error message {}", ex.getMessage());
        LOG.warn("Request description {}", webRequest.getDescription(DEFAULT_INCLUDE_CLIENT_INFO));
        return new ResponseEntity<>(
                new ErrorDto(
                        Collections.singletonList(
                                new ErrorDto.Details("SymbolNotFoundException", null, ex.getMessage())
                        )
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({AssetTrackerNotFoundException.class})
    public ResponseEntity<Object> handleAssetTrackerNotFoundException(AssetTrackerNotFoundException ex, WebRequest webRequest) {
        LOG.warn("Error message {}", ex.getMessage());
        LOG.warn("Request description: {}", webRequest.getDescription(DEFAULT_INCLUDE_CLIENT_INFO));
        return new ResponseEntity<>(
                new ErrorDto(
                        Collections.singletonList(
                                new ErrorDto.Details("SubscriptionNotFoundException", null, ex.getMessage())
                        )
                ),
                HttpStatus.NOT_FOUND
        );
    }

    public static class ErrorDto {
        private final List<Details> details;

        public ErrorDto(List<Details> details) {
            this.details = details;
        }

        public List<Details> getDetails() {
            return details;
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
