package one.digitalinnovation.gamestock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameStockExceededException extends Exception {

    public GameStockExceededException(Long id, int quantityToIncrement) {
        super(String.format("Games with %s ID to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
    }
}
