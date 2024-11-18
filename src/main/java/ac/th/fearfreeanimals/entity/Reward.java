package ac.th.fearfreeanimals.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer coinCost;

    public Reward() {}

    public Reward(String name, String description, Integer coinCost) {
        this.name = name;
        this.description = description;
        this.coinCost = coinCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(Integer coinCost) {
        this.coinCost = coinCost;
    }

    public int getRequiredPoints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRequiredPoints'");
    }

    // Getters and Setters...
}
