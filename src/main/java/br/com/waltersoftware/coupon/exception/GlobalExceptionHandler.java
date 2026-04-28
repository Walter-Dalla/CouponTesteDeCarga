package br.com.waltersoftware.coupon.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fields.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", 400,
                "error", "Validation Failed",
                "fields", fields,
                "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(BusinessDeleteOnDeleteException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessDeleteOnDelete(
            BusinessDeleteOnDeleteException ex, HttpServletRequest request) {

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", 400,
                "error", ex.getMessage(),
                "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessNotFound(
            BusinessNotFoundException ex, HttpServletRequest request) {

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", 404,
                "error", ex.getMessage(),
                "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(BusinessDuplicateCouponException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessDuplicateCoupon(
            BusinessDuplicateCouponException ex, HttpServletRequest request) {

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", 409,
                "error", ex.getMessage(),
                "path", request.getRequestURI()
        ));
    }
}
