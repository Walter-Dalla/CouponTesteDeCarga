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
@NoArgsConstructor
public class BusinessException extends Throwable {
    public BusinessException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
