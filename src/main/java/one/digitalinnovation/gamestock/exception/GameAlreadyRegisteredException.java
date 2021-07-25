package one.digitalinnovation.gamestock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameAlreadyRegisteredException extends Exception{

    public GameAlreadyRegisteredException(String gameName) {
        super(String.format("Game with name %s already registered in the system.", gameName));
    }
}
