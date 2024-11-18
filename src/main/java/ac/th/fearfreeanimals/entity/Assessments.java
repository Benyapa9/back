package ac.th.fearfreeanimals.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "assessments")
public class Assessments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer score;

    private Double fearPercentage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Assessments() {}

    public Assessments(User user, Integer score, Double fearPercentage) {
        this.user = user;
        this.score = score;
        this.fearPercentage = fearPercentage;
    }

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getFearPercentage() {
        return fearPercentage;
    }

    public void setFearPercentage(Double fearPercentage) {
        this.fearPercentage = fearPercentage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getters and Setters
}
