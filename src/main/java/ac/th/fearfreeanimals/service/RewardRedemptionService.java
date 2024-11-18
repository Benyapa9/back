package ac.th.fearfreeanimals.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ac.th.fearfreeanimals.repository.*;
import ac.th.fearfreeanimals.entity.*;

@Service
public class RewardRedemptionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardRedemptionRepository rewardRedemptionRepository;

    public RewardRedemption redeemReward(Long userId, Long rewardId) {
        // ตรวจสอบ User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().getName().equals("PATIENT")) {
            throw new RuntimeException("Patients cannot redeem rewards.");
        }

        // ตรวจสอบ Reward
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        // ตรวจสอบ Coin
        if (user.getCoins() < reward.getCoinCost()) {
            throw new RuntimeException("Insufficient coins.");
        }

        // ลด Coin
        user.setCoins(user.getCoins() - reward.getCoinCost());
        userRepository.save(user);

        // บันทึกการแลกรางวัล
        RewardRedemption redemption = new RewardRedemption(user, reward);
        return rewardRedemptionRepository.save(redemption);
    }
}
