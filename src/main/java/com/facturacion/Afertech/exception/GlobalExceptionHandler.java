package com.facturacion.Afertech.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Errores de validación Bean Validation (@Valid)
     * → 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Errores de argumentos inválidos
     * → 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Reglas de negocio / conflictos de dominio
     * (ej: dependencias, estados inválidos)
     * → 409 Conflict
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleBusinessConflict(
            IllegalStateException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    /**
     * Violaciones de integridad de datos
     * (unique constraints, FK, etc.)
     * Última defensa de la DB
     * → 409 Conflict
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(
            DataIntegrityViolationException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Operation not allowed due to data integrity rules");
    }

    /**
     * Fallback de observabilidad (QA / pruebas)
     * Captura únicamente errores NO contemplados arriba
     * ❌ No lógica de negocio
     * ❌ No interpretación
     * ❌ No masking
     * → 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnknownException(
            Exception ex
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Unexpected server error");
        body.put("message", ex.getMessage());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("timestamp", System.currentTimeMillis());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
