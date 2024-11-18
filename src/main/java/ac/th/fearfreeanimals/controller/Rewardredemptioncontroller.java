package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.entity.RewardRedemption;
import ac.th.fearfreeanimals.repository.RewardRedemptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reward-redemptions")
public class Rewardredemptioncontroller {
    private final RewardRedemptionRepository rewardRedemptionRepository;

    @Autowired
    public Rewardredemptioncontroller(RewardRedemptionRepository rewardRedemptionRepository) {
        this.rewardRedemptionRepository = rewardRedemptionRepository;
    }

    @GetMapping
    public ResponseEntity<List<RewardRedemption>> getAllRewardRedemptions() {
        List<RewardRedemption> rewardRedemptions = rewardRedemptionRepository.findAll();
        return ResponseEntity.ok(rewardRedemptions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RewardRedemption>> getRewardsByUserId(@PathVariable Long userId) {
        List<RewardRedemption> rewards = rewardRedemptionRepository.findByUserId(userId);
        return ResponseEntity.ok(rewards);
    }

    @PostMapping
    public ResponseEntity<RewardRedemption> createRewardRedemption(@RequestBody RewardRedemption rewardRedemption) {
        RewardRedemption createdReward = rewardRedemptionRepository.save(rewardRedemption);
        return ResponseEntity.ok(createdReward);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRewardRedemption(@PathVariable Long id) {
        rewardRedemptionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
