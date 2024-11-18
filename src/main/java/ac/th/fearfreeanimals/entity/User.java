package ac.th.fearfreeanimals.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(name = "is_doctor", nullable = false)
    private Boolean isDoctor = false;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "access_code", unique = true)
    private String accessCode;

    @Column(name = "fear_level", nullable = false)
    private Integer fearLevel = 0;

    @Column(name = "coins", nullable = false)
    private Integer coins = 0;
   

    @Column(name = "fear_percentage", nullable = true)
    private Double fearPercentage;
    

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<GameProgress> gameProgresses;

    public User() {}

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Integer getFearLevel() {
        return fearLevel;
    }

    public void setFearLevel(Integer fearLevel) {
        this.fearLevel = fearLevel;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public List<GameProgress> getGameProgresses() {
        return gameProgresses;
    }

    public void setGameProgresses(List<GameProgress> gameProgresses) {
        this.gameProgresses = gameProgresses;
    }

    public Boolean getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(Boolean isDoctor) {
        this.isDoctor = isDoctor;
    }

    public Double getFearPercentage() {
        return fearPercentage;
    }

    public void setFearPercentage(Double fearPercentage) {
        this.fearPercentage = fearPercentage;
    }
}
