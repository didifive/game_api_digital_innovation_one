package one.digitalinnovation.gamestock.controller;

import lombok.AllArgsConstructor;
import one.digitalinnovation.gamestock.dto.GameDTO;
import one.digitalinnovation.gamestock.dto.QuantityDTO;
import one.digitalinnovation.gamestock.exception.GameAlreadyRegisteredException;
import one.digitalinnovation.gamestock.exception.GameNotFoundException;
import one.digitalinnovation.gamestock.exception.GameStockExceededException;
import one.digitalinnovation.gamestock.exception.GameStockLoweredException;
import one.digitalinnovation.gamestock.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameController implements GameControllerDocs {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO createGame(@RequestBody @Valid GameDTO gameDTO) throws GameAlreadyRegisteredException, GameStockLoweredException {
        return gameService.createGame(gameDTO);
    }

    @GetMapping("/name/{name}")
    public GameDTO findByName(@PathVariable String name) throws GameNotFoundException {
        return gameService.findByName(name);
    }

    @GetMapping("/id/{id}")
    public GameDTO findById(@PathVariable Long id) throws GameNotFoundException {
        return gameService.findById(id);
    }

    @GetMapping
    public List<GameDTO> listGames() {
        return gameService.listAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws GameNotFoundException {
        gameService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public GameDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws GameNotFoundException, GameStockExceededException {
        return gameService.increment(id, quantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    public GameDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws GameNotFoundException, GameStockLoweredException {
        return gameService.decrement(id, quantityDTO.getQuantity());
    }
}
