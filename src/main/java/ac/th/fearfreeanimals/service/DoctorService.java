package ac.th.fearfreeanimals.service;

import ac.th.fearfreeanimals.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.th.fearfreeanimals.entity.*;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GameProgressRepository gameProgressRepository;

    /**
     * Add a new patient
     */
    public User addPatient(User patient) {
        // Set role to PATIENT
        Role patientRole = roleRepository.findByName("PATIENT")
                .orElseThrow(() -> new RuntimeException("Role PATIENT not found"));

        patient.setRole(patientRole);

        // Save the patient to get the ID
        User createdPatient = userRepository.save(patient);

        // Generate Access Code
        String accessCode = "FFANM" + String.format("%03d", createdPatient.getId());
        createdPatient.setAccessCode(accessCode);

        // Save updated access code
        userRepository.save(createdPatient);

        // Create initial GameProgress
        GameProgress gameProgress = new GameProgress(createdPatient, null); // Animal type is null initially
        gameProgress.setCurrentLevel(1); // Start at level 1
        gameProgress.setCompleted(false); // Game not completed
        gameProgressRepository.save(gameProgress);

        return createdPatient;
    }

    /**
     * Set animal type for the game
     */
    public GameProgress setAnimalType(Long userId, String animalType) {
        // Find the game progress by user ID
        GameProgress gameProgress = gameProgressRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("GameProgress not found for user ID " + userId));

        // Set the animal type
        gameProgress.setAnimalType(animalType);

        // Save the updated progress
        return gameProgressRepository.save(gameProgress);
    }

    /**
     * Start game at level 1
     */
    public GameProgress startGame(Long userId) {
        // Ensure the game progress exists
        GameProgress gameProgress = gameProgressRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("GameProgress not found for user ID " + userId));

        // Set game to level 1 and mark as not completed
        gameProgress.setCurrentLevel(1);
        gameProgress.setCompleted(false);

        // Save and return updated progress
        return gameProgressRepository.save(gameProgress);
    }
}
