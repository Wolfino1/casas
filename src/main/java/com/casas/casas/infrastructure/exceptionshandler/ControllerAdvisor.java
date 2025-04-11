package com.casas.casas.infrastructure.exceptionshandler;

import com.casas.casas.domain.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(NameMaxSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleNameMaxSizeExceededException(NameMaxSizeExceededException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.NAME_MAX_SIZE_MESSAGE,
                LocalDateTime.now()));
    }

    @ExceptionHandler(DescriptionMaxSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleDescriptionMaxSizeExceededException(DescriptionMaxSizeExceededException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.DESCRIPTION_MAX_SIZE_MESSAGE,
                LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.CATEGORY_EXISTS_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(LocationAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleLocationAlreadyExistsException(LocationAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.LOCATION_EXISTS_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(NullException.class)
    public ResponseEntity<ExceptionResponse> handleNullException(NullException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(exception.getMessage(),
                LocalDateTime.now()));
    }
    @ExceptionHandler(EmptyException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyException(EmptyException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(exception.getMessage(),
                LocalDateTime.now()));
    }
    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPaginationException(InvalidPaginationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(exception.getMessage(),
                LocalDateTime.now()));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(exception.getMessage(), LocalDateTime.now())
        );
    }
}