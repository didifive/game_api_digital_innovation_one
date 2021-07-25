package one.digitalinnovation.gamestock.service;

import one.digitalinnovation.gamestock.builder.GameDTOBuilder;
import one.digitalinnovation.gamestock.dto.GameDTO;
import one.digitalinnovation.gamestock.entity.Game;
import one.digitalinnovation.gamestock.exception.GameAlreadyRegisteredException;
import one.digitalinnovation.gamestock.exception.GameNotFoundException;
import one.digitalinnovation.gamestock.exception.GameStockExceededException;
import one.digitalinnovation.gamestock.exception.GameStockLoweredException;
import one.digitalinnovation.gamestock.mapper.GameMapper;
import one.digitalinnovation.gamestock.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    private static final long INVALID_GAME_ID = 1L;

    @Mock
    private GameRepository gameRepository;

    private final GameMapper gameMapper = GameMapper.INSTANCE;

    @InjectMocks
    private GameService gameService;

    @Test
    void whenGameInformedThenItShouldBeCreated() throws GameAlreadyRegisteredException, GameStockLoweredException {
        // given
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedSavedGame = gameMapper.toModel(expectedGameDTO);

        // when
        when(gameRepository.findByName(expectedGameDTO.getName())).thenReturn(Optional.empty());
        when(gameRepository.save(expectedSavedGame)).thenReturn(expectedSavedGame);

        //then
        GameDTO createdGameDTO = gameService.createGame(expectedGameDTO);

        assertThat(createdGameDTO.getId(), is(equalTo(expectedGameDTO.getId())));
        assertThat(createdGameDTO.getName(), is(equalTo(expectedGameDTO.getName())));
        assertThat(createdGameDTO.getConsole(), is(equalTo(expectedGameDTO.getConsole())));
        assertThat(createdGameDTO.getQuantity(), is(equalTo(expectedGameDTO.getQuantity())));
        assertThat(createdGameDTO.getMin(), is(equalTo(expectedGameDTO.getMin())));
        assertThat(createdGameDTO.getMax(), is(equalTo(expectedGameDTO.getMax())));
        assertThat(createdGameDTO.getGenre(), is(equalTo(expectedGameDTO.getGenre())));
    }

    @Test
    void whenAlreadyRegisteredGameInformedThenAnExceptionShouldBeThrown() {
        // given
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game duplicatedGame = gameMapper.toModel(expectedGameDTO);

        // when
        when(gameRepository.findByName(expectedGameDTO.getName())).thenReturn(Optional.of(duplicatedGame));

        // then
        assertThrows(GameAlreadyRegisteredException.class, () -> gameService.createGame(expectedGameDTO));
    }

    @Test
    void whenValidGameNameIsGivenThenReturnAGame() throws GameNotFoundException {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedFoundGame = gameMapper.toModel(expectedFoundGameDTO);

        // when
        when(gameRepository.findByName(expectedFoundGame.getName())).thenReturn(Optional.of(expectedFoundGame));

        // then
        GameDTO foundGameDTO = gameService.findByName(expectedFoundGameDTO.getName());

        assertThat(foundGameDTO, is(equalTo(expectedFoundGameDTO)));
    }

    @Test
    void whenValidGameIdIsGivenThenReturnAGame() throws GameNotFoundException {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedFoundGame = gameMapper.toModel(expectedFoundGameDTO);

        // when
        when(gameRepository.findById(expectedFoundGame.getId())).thenReturn(Optional.of(expectedFoundGame));

        // then
        GameDTO foundGameDTO = gameService.findById(expectedFoundGameDTO.getId());

        assertThat(foundGameDTO, is(equalTo(expectedFoundGameDTO)));
    }

    @Test
    void whenNotRegisteredGameNameIsGivenThenThrowAnException() {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();

        // when
        when(gameRepository.findByName(expectedFoundGameDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(GameNotFoundException.class, () -> gameService.findByName(expectedFoundGameDTO.getName()));
    }

    @Test
    void whenListGameIsCalledThenReturnAListOfGames() {
        // given
        GameDTO expectedFoundGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedFoundGame = gameMapper.toModel(expectedFoundGameDTO);

        //when
        when(gameRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundGame));

        //then
        List<GameDTO> foundListGamesDTO = gameService.listAll();

        assertThat(foundListGamesDTO, is(not(empty())));
        assertThat(foundListGamesDTO.get(0), is(equalTo(expectedFoundGameDTO)));
    }

    @Test
    void whenListGameIsCalledThenReturnAnEmptyListOfGames() {
        //when
        when(gameRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<GameDTO> foundListGamesDTO = gameService.listAll();

        assertThat(foundListGamesDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAGameShouldBeDeleted() throws GameNotFoundException {
        // given
        GameDTO expectedDeletedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedDeletedGame = gameMapper.toModel(expectedDeletedGameDTO);

        // when
        when(gameRepository.findById(expectedDeletedGameDTO.getId())).thenReturn(Optional.of(expectedDeletedGame));
        doNothing().when(gameRepository).deleteById(expectedDeletedGameDTO.getId());

        // then
        gameService.deleteById(expectedDeletedGameDTO.getId());

        verify(gameRepository, times(1)).findById(expectedDeletedGameDTO.getId());
        verify(gameRepository, times(1)).deleteById(expectedDeletedGameDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementGameStock() throws GameNotFoundException, GameStockExceededException {
        //given
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        //when
        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));
        when(gameRepository.save(expectedGame)).thenReturn(expectedGame);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedGameDTO.getQuantity() + quantityToIncrement;

        // then
        GameDTO incrementedGameDTO = gameService.increment(expectedGameDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedGameDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedGameDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));

        int quantityToIncrement = 80;
        assertThrows(GameStockExceededException.class, () -> gameService.increment(expectedGameDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));

        int quantityToIncrement = 45;
        assertThrows(GameStockExceededException.class, () -> gameService.increment(expectedGameDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(gameRepository.findById(INVALID_GAME_ID)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.increment(INVALID_GAME_ID, quantityToIncrement));
    }

    @Test
    void whenDecrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToDecrement = 10;

        when(gameRepository.findById(INVALID_GAME_ID)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.decrement(INVALID_GAME_ID, quantityToDecrement));
    }

    @Test
    void whenDecrementIsGreatherThanQuantityThenThrowException() {
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));

        int quantityToDecrement = 80;

        assertThat(quantityToDecrement, greaterThanOrEqualTo(expectedGameDTO.getQuantity()));

        assertThrows(GameStockExceededException.class, () -> gameService.increment(expectedGameDTO.getId(), quantityToDecrement));
    }

    @Test
    void whenDecrementAfterSubtractionIsLowerThanMinThenThrowException() {
        GameDTO expectedGameDTO = GameDTOBuilder.builder().build().toGameDTO();
        Game expectedGame = gameMapper.toModel(expectedGameDTO);

        when(gameRepository.findById(expectedGameDTO.getId())).thenReturn(Optional.of(expectedGame));

        int quantityToDecrement = 8;
        assertThrows(GameStockLoweredException.class, () -> gameService.decrement(expectedGameDTO.getId(), quantityToDecrement));
    }
}
