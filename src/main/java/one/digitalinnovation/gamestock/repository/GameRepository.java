package one.digitalinnovation.gamestock.repository;

import one.digitalinnovation.gamestock.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByName(String name);

}
