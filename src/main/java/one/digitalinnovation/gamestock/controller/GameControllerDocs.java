package one.digitalinnovation.gamestock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.digitalinnovation.gamestock.dto.GameDTO;
import one.digitalinnovation.gamestock.exception.GameAlreadyRegisteredException;
import one.digitalinnovation.gamestock.exception.GameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages game stock")
public interface GameControllerDocs {

    @ApiOperation(value = "Game creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success game creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    GameDTO createGame(GameDTO gameDTO) throws GameAlreadyRegisteredException;

    @ApiOperation(value = "Returns game found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success game found in the system"),
            @ApiResponse(code = 404, message = "Game with given name not found.")
    })
    GameDTO findByName(@PathVariable String name) throws GameNotFoundException;

    @ApiOperation(value = "Returns game found by a given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success game found in the system"),
            @ApiResponse(code = 404, message = "Game with given id not found.")
    })
    GameDTO findById(@PathVariable Long id) throws GameNotFoundException;

    @ApiOperation(value = "Returns a list of all games registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all games registered in the system"),
    })
    List<GameDTO> listGames();

    @ApiOperation(value = "Delete a game found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success game deleted in the system"),
            @ApiResponse(code = 404, message = "Game with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws GameNotFoundException;
}
