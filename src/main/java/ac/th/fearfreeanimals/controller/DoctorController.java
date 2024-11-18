package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.entity.GameProgress;
import ac.th.fearfreeanimals.entity.User;
import ac.th.fearfreeanimals.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * Add a new patient
     */
    @PostMapping("/add-patient")
    public ResponseEntity<User> addPatient(@RequestBody User patient) {
        User createdPatient = doctorService.addPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    /**
     * Set animal type for the patient
     */
    @PutMapping("/set-animal/{userId}")
    public ResponseEntity<GameProgress> setAnimalType(@PathVariable Long userId, @RequestParam String animalType) {
        GameProgress updatedProgress = doctorService.setAnimalType(userId, animalType);
        return ResponseEntity.ok(updatedProgress);
    }

    /**
     * Start the game for the patient
     */
    @PostMapping("/start-game/{userId}")
    public ResponseEntity<GameProgress> startGame(@PathVariable Long userId) {
        GameProgress startedProgress = doctorService.startGame(userId);
        return ResponseEntity.ok(startedProgress);
    }
}
