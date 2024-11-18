package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.entity.Reward;
import ac.th.fearfreeanimals.entity.RewardRedemption;
import ac.th.fearfreeanimals.repository.RewardRepository;
import ac.th.fearfreeanimals.repository.RewardRedemptionRepository;
import ac.th.fearfreeanimals.repository.UserRepository;
import ac.th.fearfreeanimals.entity.User; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")  // กำหนดเส้นทางหลักสำหรับคำขอทั้งหมดที่เกี่ยวข้องกับรางวัล
public class RewardController {

    private final RewardRepository rewardRepository;
    private final RewardRedemptionRepository rewardRedemptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public RewardController(RewardRepository rewardRepository, RewardRedemptionRepository rewardRedemptionRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.rewardRedemptionRepository = rewardRedemptionRepository;
        this.userRepository = userRepository; // เพิ่มการ inject userRepository
    }

    // Endpoint เพื่อดึงข้อมูลรางวัลทั้งหมด
    @GetMapping
    public ResponseEntity<List<Reward>> getAllRewards() {
        List<Reward> rewards = rewardRepository.findAll();
        return ResponseEntity.ok(rewards);
    }

    // Endpoint เพื่อดึงข้อมูลรางวัลตาม ID
    @GetMapping("/{id}")
    public ResponseEntity<Reward> getRewardById(@PathVariable Long id) {
        Reward reward = rewardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reward not found with id " + id));
        return ResponseEntity.ok(reward);
    }

    // Endpoint เพื่อสร้างรางวัลใหม่
    @PostMapping
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        Reward createdReward = rewardRepository.save(reward);
        return ResponseEntity.ok(createdReward);
    }

    @PostMapping("/redeem/{userId}")
    public ResponseEntity<RewardRedemption> redeemReward(@PathVariable Long userId, @RequestBody RewardRedemption rewardRedemption) {
        // ค้นหาผู้ใช้
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    
        // ตั้งค่าผู้ใช้ใน RewardRedemption
        rewardRedemption.setUser(user); 
    
        // บันทึกข้อมูลการแลกรางวัล
        RewardRedemption redeemed = rewardRedemptionRepository.save(rewardRedemption);
        return ResponseEntity.ok(redeemed);
    }

    // Endpoint เพื่อดึงข้อมูลการแลกรางวัลทั้งหมด
    @GetMapping("/redemptions")
    public ResponseEntity<List<RewardRedemption>> getAllRedemptions() {
        List<RewardRedemption> redemptions = rewardRedemptionRepository.findAll();
        return ResponseEntity.ok(redemptions);
    }
}
