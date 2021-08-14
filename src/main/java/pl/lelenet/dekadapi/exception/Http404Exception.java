package pl.lelenet.dekadapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Resource Not Found")
public class Http404Exception extends RuntimeException {
    private final String message;

    public Http404Exception(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
