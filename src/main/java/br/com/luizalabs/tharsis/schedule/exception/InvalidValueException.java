package br.com.luizalabs.tharsis.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidValueException extends RuntimeException {

    private static final long serialVersionUID = 7554124793429918282L;

    public InvalidValueException(String message) {
        super(message);
    }
}
