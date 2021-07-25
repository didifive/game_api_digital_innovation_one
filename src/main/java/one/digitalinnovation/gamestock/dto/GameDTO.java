package one.digitalinnovation.gamestock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovation.gamestock.enums.GameGenre;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Size(min = 1, max = 200)
    private String console;

    @NotNull
    @Max(499)
    private Integer min;

    @NotNull
    @Max(500)
    private Integer max;

    @NotNull
    @Max(500)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GameGenre genre;
}
