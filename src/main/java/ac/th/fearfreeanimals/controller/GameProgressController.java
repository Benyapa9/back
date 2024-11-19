package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.entity.GameProgress;
import ac.th.fearfreeanimals.repository.GameProgressRepository;
import ac.th.fearfreeanimals.repository.UserRepository;
import ac.th.fearfreeanimals.service.GameProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/game-progress")
public class GameProgressController {

    @Autowired
    private GameProgressService gameProgressService;

    @Autowired
    private GameProgressRepository gameProgressRepository;
    @Autowired
    private UserRepository userRepository;
    // Get game progress by userId
    // @GetMapping("/{userId}")
    // public ResponseEntity<GameProgress> getGameProgress(@PathVariable Long userId) {
    //     Optional<GameProgress> progress = gameProgressRepository.findByUserId(userId);

    //     if (progress.isPresent()) {
    //         return ResponseEntity.ok(progress.get());
    //     } else {
    //         // Automatically create a new progress if it doesn't exist
    //         GameProgress newProgress = new GameProgress();
    //         newProgress.setUser(userRepository.findById(userId)
    //                 .orElseThrow(() -> new RuntimeException("User not found with ID " + userId)));
    //         newProgress.setAnimalType("default"); // Set a default animal type
    //         newProgress.setCurrentLevel(1);
    //         newProgress.setCompleted(false);

    //         gameProgressRepository.save(newProgress);
    //         return ResponseEntity.ok(newProgress);
    //     }
    // }

    // Create game progress
    @PostMapping("/{userId}")
    public ResponseEntity<GameProgress> createGameProgress(
            @PathVariable Long userId,
            @RequestBody GameProgress newProgress) {
        GameProgress createdProgress = gameProgressService.createGameProgress(userId, newProgress);
        return ResponseEntity.ok(createdProgress);
    }

    // Update game progress
    // @PutMapping("/{userId}")
    // public ResponseEntity<GameProgress> updateGameProgress(
    //         @PathVariable Long userId,
    //         @RequestBody GameProgress gameProgressDetails) {
    //     GameProgress updatedProgress = gameProgressService.updateGameProgress(userId, gameProgressDetails);
    //     return ResponseEntity.ok(updatedProgress);
    // }

    // Move to the next level
    @PutMapping("/next-level/{userId}")
    public ResponseEntity<GameProgress> nextLevel(@PathVariable Long userId) {
        GameProgress updatedProgress = gameProgressService.nextLevel(userId);
        return ResponseEntity.ok(updatedProgress);
    }
    

    
    @PutMapping("/progress/general/{userId}")
    public ResponseEntity<GameProgress> updateGameProgressForGeneralUser(
            @PathVariable Long userId,
            @RequestParam String animalType,
            @RequestParam int level) {
        System.out.println("UserId: " + userId);
        System.out.println("AnimalType: " + animalType);
        System.out.println("Level: " + level);

        GameProgress progress = gameProgressService.updateGameProgressForGeneralUser(userId, animalType, level);
        return ResponseEntity.ok(progress);
    }
// ดึงความคืบหน้าเกม
    @GetMapping("/{userId}")
    public ResponseEntity<GameProgress> getGameProgress(@PathVariable Long userId) {
        GameProgress progress = gameProgressService.getOrCreateGameProgress(userId, "default");
        return ResponseEntity.ok(progress);
    }

    // อัปเดตความคืบหน้าเกม
    @PutMapping("/{userId}/update")
    public ResponseEntity<GameProgress> updateGameProgress(
            @PathVariable Long userId,
            @RequestParam String animalType,
            @RequestParam int level) {
        if (level < 1) {
            return ResponseEntity.badRequest().body(null);
        }

        GameProgress progress = gameProgressService.updateGameProgress(userId, animalType, level);
        return ResponseEntity.ok(progress);
    }
    

    // ปลดล็อกเลเวลถัดไป
    @PutMapping("/{userId}/next-level")
    public ResponseEntity<GameProgress> unlockNextLevel(@PathVariable Long userId) {
        GameProgress progress = gameProgressService.unlockNextLevel(userId);
        return ResponseEntity.ok(progress);
    }
    
    //ใส่ข้อมูล
    @PutMapping("/{userId}/update-symptom")
    public ResponseEntity<GameProgress> updateSymptomNotes(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> payload) {
        String animalType = (String) payload.get("animal");
        int level = (int) payload.get("level");
        String text = (String) payload.get("text");

        GameProgress progress = gameProgressService.createOrUpdateProgress(userId, animalType, level, text);
        return ResponseEntity.ok(progress);
    }
    
    // @GetMapping("/{userId}/{animal}")
    // public ResponseEntity<Map<String, Object>> getGameProgress(
    //         @PathVariable Long userId,
    //         @PathVariable String animal) {
    //     // ดึงข้อมูล GameProgress
    //     GameProgress progress = gameProgressService.getGameProgress(userId, animal);

    //     // จัดรูปแบบ Response
    //     Map<String, Object> response = new HashMap<>();
    //     response.put("id", progress.getId());
    //     response.put("user", Map.of(
    //             "id", progress.getUser().getId(),
    //             "username", progress.getUser().getUsername()));
    //     response.put("animalType", progress.getAnimalType());
    //     response.put("currentLevel", progress.getCurrentLevel());
    //     response.put("symptomNotes", progress.getSymptomNotes());

    //     return ResponseEntity.ok(response);
    // }

    // Get game progress by userId and animalType
    @GetMapping("/{userId}/{animal}")
    public ResponseEntity<GameProgress> getGameProgress(
            @PathVariable Long userId,
            @PathVariable String animal) {
        GameProgress progress = gameProgressService.getGameProgress(userId, animal);
        return ResponseEntity.ok(progress);
    }
    
    // Update game progress
    @PutMapping("/{userId}/update")
    public ResponseEntity<GameProgress> updateGameProgress(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> payload) {
        String animal = (String) payload.get("animal");
        int level = (int) payload.get("level");
        GameProgress progress = gameProgressService.updateGameProgress(userId, animal, level);
        return ResponseEntity.ok(progress);
    }

}
