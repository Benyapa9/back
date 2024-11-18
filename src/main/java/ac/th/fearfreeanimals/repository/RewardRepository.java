package ac.th.fearfreeanimals.repository;

import ac.th.fearfreeanimals.entity.Reward;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

}
