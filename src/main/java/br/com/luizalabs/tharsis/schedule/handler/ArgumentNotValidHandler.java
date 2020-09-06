package br.com.luizalabs.tharsis.schedule.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArgumentNotValidHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<Error> handle(MethodArgumentNotValidException expection) {

        List<Error> erros = new ArrayList<>();

        List<FieldError> fieldErrors = expection.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            erros.add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return erros;
    }

    public class Error {
        private String field;
        private String message;

        public Error(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
