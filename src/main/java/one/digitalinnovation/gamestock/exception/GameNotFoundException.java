package one.digitalinnovation.gamestock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends Exception {

    public GameNotFoundException(String gameName) {
        super(String.format("Game with name %s not found in the system.", gameName));
    }

    public GameNotFoundException(Long id) {
        super(String.format("Game with id %s not found in the system.", id));
    }
}
