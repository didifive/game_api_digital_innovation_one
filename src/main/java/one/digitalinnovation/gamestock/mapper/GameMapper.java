package one.digitalinnovation.gamestock.mapper;

import one.digitalinnovation.gamestock.dto.GameDTO;
import one.digitalinnovation.gamestock.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    Game toModel(GameDTO gameDTO);

    GameDTO toDTO(Game game);
}
