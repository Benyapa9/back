package ac.th.fearfreeanimals.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "reward_redemptions")
public class RewardRedemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reward_id", nullable = false) // This is enough to map the reward relationship
    private Reward reward;

    @Column(nullable = false)
    private LocalDateTime redeemedAt = LocalDateTime.now();

    public RewardRedemption() {}

    public RewardRedemption(User user, Reward reward) {
        this.user = user;
        this.reward = reward;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public LocalDateTime getRedeemedAt() {
        return redeemedAt;
    }

    public void setRedeemedAt(LocalDateTime redeemedAt) {
        this.redeemedAt = redeemedAt;
    }
}
