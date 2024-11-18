package ac.th.fearfreeanimals.repository;

import ac.th.fearfreeanimals.entity.RewardRedemption;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Long> {
    List<RewardRedemption> findByUserId(Long userId);
}
