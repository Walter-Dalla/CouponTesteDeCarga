package br.com.waltersoftware.coupon.exception;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@NoArgsConstructor
public class ResourceNotFoundException  extends Throwable {
    public ResourceNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
