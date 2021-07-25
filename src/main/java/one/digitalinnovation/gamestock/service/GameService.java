package one.digitalinnovation.gamestock.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.gamestock.dto.GameDTO;
import one.digitalinnovation.gamestock.entity.Game;
import one.digitalinnovation.gamestock.exception.GameAlreadyRegisteredException;
import one.digitalinnovation.gamestock.exception.GameNotFoundException;
import one.digitalinnovation.gamestock.exception.GameStockExceededException;
import one.digitalinnovation.gamestock.exception.GameStockLoweredException;
import one.digitalinnovation.gamestock.mapper.GameMapper;
import one.digitalinnovation.gamestock.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper = GameMapper.INSTANCE;

    public GameDTO createGame(GameDTO gameDTO) throws GameAlreadyRegisteredException, GameStockLoweredException {
        verifyIfIsAlreadyRegistered(gameDTO.getName());
        Game game = gameMapper.toModel(gameDTO);
        if (game.getQuantity() >= game.getMin()) {
            Game savedGame = gameRepository.save(game);
            return gameMapper.toDTO(savedGame);
        }
        throw new GameStockLoweredException(game.getId(), game.getQuantity(), game.getMin());
    }

    public GameDTO findByName(String name) throws GameNotFoundException {
        Game foundGame = gameRepository.findByName(name)
                .orElseThrow(() -> new GameNotFoundException(name));
        return gameMapper.toDTO(foundGame);
    }

    public GameDTO findById(Long id) throws GameNotFoundException {
        Game foundGame = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        return gameMapper.toDTO(foundGame);
    }

    public List<GameDTO> listAll() {
        return gameRepository.findAll()
                .stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws GameNotFoundException {
        verifyIfExists(id);
        gameRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws GameAlreadyRegisteredException {
        Optional<Game> optSavedBeer = gameRepository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new GameAlreadyRegisteredException(name);
        }
    }

    private Game verifyIfExists(Long id) throws GameNotFoundException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    public GameDTO increment(Long id, int quantityToIncrement) throws GameNotFoundException, GameStockExceededException {
        Game gameToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + gameToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= gameToIncrementStock.getMax()) {
            gameToIncrementStock.setQuantity(gameToIncrementStock.getQuantity() + quantityToIncrement);
            Game incrementedGameStock = gameRepository.save(gameToIncrementStock);
            return gameMapper.toDTO(incrementedGameStock);
        }
        throw new GameStockExceededException(id, quantityToIncrement);
    }

    public GameDTO decrement(Long id, int quantityToDecrement) throws GameNotFoundException, GameStockLoweredException {
        Game gameToDecrementStock = verifyIfExists(id);
        int quantityAfterDecrement = gameToDecrementStock.getQuantity() - quantityToDecrement;
        if (quantityAfterDecrement >= gameToDecrementStock.getMin()) {
            gameToDecrementStock.setQuantity(quantityAfterDecrement);
            Game decrementedGameStock = gameRepository.save(gameToDecrementStock);
            return gameMapper.toDTO(decrementedGameStock);
        }
        throw new GameStockLoweredException(id, quantityToDecrement, gameToDecrementStock.getMin());
    }
}
