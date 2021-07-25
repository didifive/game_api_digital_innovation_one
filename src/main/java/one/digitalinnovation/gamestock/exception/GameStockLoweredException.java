package one.digitalinnovation.gamestock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameStockLoweredException extends Exception {

    public GameStockLoweredException(Long id, int quantityToDecrement, int minStock) {
        super(String.format("Amount of decrement informed: %s, for the game with %s id, is not allowed, as it results in quantity less than the minimum stock capacity: %s", quantityToDecrement, id, minStock));
    }
}
