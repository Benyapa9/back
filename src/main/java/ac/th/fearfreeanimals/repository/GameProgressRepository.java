package ac.th.fearfreeanimals.repository;

import ac.th.fearfreeanimals.entity.GameProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameProgressRepository extends JpaRepository<GameProgress, Long> {
    Optional<GameProgress> findByUserId(Long userId);

    Optional<GameProgress> findByUserIdAndAnimalType(Long userId, String animalType);
}
