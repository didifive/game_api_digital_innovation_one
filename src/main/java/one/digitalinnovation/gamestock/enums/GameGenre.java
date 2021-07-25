package one.digitalinnovation.gamestock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameGenre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    RPG("Role-Playing"),
    SIMULATION("Simulation"),
    STRATEGY("Strategy"),
    SPORTS("Sport"),
    MMO("Massively Multiplayer Online");
//  Source: https://en.wikipedia.org/wiki/List_of_video_game_genres

    private final String description;
}
