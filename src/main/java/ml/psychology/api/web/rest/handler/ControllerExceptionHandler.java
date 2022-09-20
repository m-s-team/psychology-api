package ml.psychology.api.web.rest.handler;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice // @RestControllerAdvice is the combination of both @ControllerAdvice and @ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = "application/json")
    public ResponseEntity<ErrorMessage> badRequest(ConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(e.getMessage()));
    }
}
