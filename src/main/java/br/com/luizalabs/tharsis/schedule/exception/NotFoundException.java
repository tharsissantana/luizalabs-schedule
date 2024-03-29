package br.com.luizalabs.tharsis.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7554124793429918282L;

    public NotFoundException(String message) {
        super(message);
    }
}
