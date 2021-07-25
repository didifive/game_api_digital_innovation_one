package one.digitalinnovation.gamestock.builder;

import lombok.Builder;
import one.digitalinnovation.gamestock.dto.GameDTO;
import one.digitalinnovation.gamestock.enums.GameGenre;

@Builder
public class GameDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Final Fantasy II";

    @Builder.Default
    private final String console = "Super Nintendo";

    @Builder.Default
    private final int min = 10;

    @Builder.Default
    private final int max = 50;

    @Builder.Default
    private final int quantity = 15;

    @Builder.Default
    private final GameGenre genre = GameGenre.RPG;

    public GameDTO toGameDTO() {
        return new GameDTO(id,
                name,
                console,
                min,
                max,
                quantity,
                genre);
    }
}
