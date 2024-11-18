package ac.th.fearfreeanimals.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;

import java.util.Map;

@Entity
@Table(name = "game_progress")
public class GameProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // เชื่อมโยงกับ User ที่เล่นเกมนี้

    @Column(name = "animal_type", nullable = false)
    private String animalType; // ประเภทสัตว์ที่เลือก เช่น งู, นก, แมว

    @Column(name = "current_level", nullable = false)
    private Integer currentLevel = 1; // ด่านเริ่มต้นคือด่าน 1

    @Column(name = "completed", nullable = false)
    private Boolean completed = false; // ระบุสถานะว่าเกมเสร็จสมบูรณ์หรือยัง

    @Column(columnDefinition = "TEXT")
    private String description; // ข้อมูลคำอธิบาย (เฉพาะผู้ป่วย)

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now(); // เก็บวันที่อัปเดตครั้งล่าสุด

   

    @ElementCollection
    @CollectionTable(name = "symptom_notes", joinColumns = @JoinColumn(name = "game_progress_id"))
    @MapKeyColumn(name = "level")
    @Column(name = "notes", columnDefinition = "TEXT")
    private Map<Integer, String> symptomNotes = new HashMap<>();

    public GameProgress() {
    }

    public GameProgress(User user, String animalType) {
        this.user = user;
        this.animalType = animalType;
    }

    // Getter และ Setter

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

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        if (animalType == null || animalType.isEmpty()) {
            throw new IllegalArgumentException("Animal type cannot be null or empty.");
        }
        this.animalType = animalType;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        if (currentLevel == null || currentLevel < 1) {
            throw new IllegalArgumentException("Current level must be greater than or equal to 1.");
        }
        this.currentLevel = currentLevel;
        this.lastUpdated = LocalDateTime.now(); // อัปเดตเวลาเมื่อเปลี่ยนเลเวล
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
        this.lastUpdated = LocalDateTime.now(); // อัปเดตเวลาเมื่อเปลี่ยนสถานะ
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("Description exceeds the maximum length of 500 characters.");
        }
        this.description = description;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Getter และ Setter สำหรับ symptomNotes
    public Map<Integer, String> getSymptomNotes() {
        return symptomNotes;
    }

    public void setSymptomNotes(Map<Integer, String> symptomNotes) {
        this.symptomNotes = symptomNotes;
    }
}
