package ac.th.fearfreeanimals.service;

import ac.th.fearfreeanimals.entity.GameProgress;
import ac.th.fearfreeanimals.entity.User;
import ac.th.fearfreeanimals.repository.GameProgressRepository;
import ac.th.fearfreeanimals.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameProgressService {

    @Autowired
    private GameProgressRepository gameProgressRepository;

    @Autowired
    private UserRepository userRepository;

    public GameProgress createGameProgress(Long userId, GameProgress newProgress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));
        newProgress.setUser(user);
        return gameProgressRepository.save(newProgress);
    }

    public GameProgress updateGameProgress(Long userId, GameProgress gameProgressDetails) {
        GameProgress gameProgress = gameProgressRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Game progress not found for user ID " + userId));

        gameProgress.setCurrentLevel(gameProgressDetails.getCurrentLevel());
        gameProgress.setAnimalType(gameProgressDetails.getAnimalType());
        gameProgress.setCompleted(gameProgressDetails.getCompleted());
        gameProgress.setDescription(gameProgressDetails.getDescription());
        return gameProgressRepository.save(gameProgress);
    }

    public GameProgress nextLevel(Long userId) {
        GameProgress gameProgress = gameProgressRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Game progress not found for user ID " + userId));

        int currentLevel = gameProgress.getCurrentLevel();
        gameProgress.setCurrentLevel(currentLevel + 1);

        if (currentLevel == 10) {
            // ไม่มีการเพิ่มเหรียญให้ผู้ป่วย
            User user = gameProgress.getUser();
            if (!user.getRole().getName().equals("PATIENT")) {
                user.setCoins(user.getCoins() + 1);
                userRepository.save(user);
            }
        }

        return gameProgressRepository.save(gameProgress);
    }

    public GameProgress getGameProgress(Long userId) {
        return gameProgressRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Game progress not found for user ID " + userId));
    }
    
//     public GameProgress updateSymptomNotes(Long userId, int level, String TEXT) {
//     GameProgress progress = gameProgressRepository.findByUserId(userId)
//             .orElseThrow(() -> new RuntimeException("Game progress not found for user ID " + userId));

//     // เพิ่มหรืออัปเดต symptom notes
//     progress.getSymptomNotes().put(level, TEXT);
//     progress.setLastUpdated(LocalDateTime.now());
//     return gameProgressRepository.save(progress);
// }
public GameProgress updateSymptomNotes(Long userId, String animal, int level, String text) {
    GameProgress progress = gameProgressRepository.findByUserIdAndAnimalType(userId, animal)
            .orElseThrow(() -> new RuntimeException("Game progress not found for user ID " + userId));

    if (progress.getSymptomNotes() == null) {
        progress.setSymptomNotes(new HashMap<>()); // ถ้า null ให้สร้างใหม่
    }

    progress.getSymptomNotes().put(level, text); // เพิ่มหรืออัปเดตโน้ตในเลเวลที่กำหนด
    return gameProgressRepository.save(progress);
}


// public GameProgress updateGameProgress(Long userId, String animalType, int level) {
//     // ดึงข้อมูลเกมความคืบหน้าตาม userId
//     GameProgress progress = gameProgressRepository.findByUserIdAndAnimalType(userId, animalType)
//             .orElseThrow(() -> new RuntimeException("Game progress not found"));

//     // อัปเดตเลเวล
//     progress.setCurrentLevel(level);

//     // บันทึกข้อมูลที่อัปเดต
//     return gameProgressRepository.save(progress);
// }

public GameProgress updateGameProgressForGeneralUser(Long userId, String animalType, int level) {
    GameProgress progress = gameProgressRepository.findByUserIdAndAnimalType(userId, animalType)
            .orElseThrow(() -> new RuntimeException("Game progress not found"));

    if (level > progress.getCurrentLevel()) {
        progress.setCurrentLevel(level);
        gameProgressRepository.save(progress);
    }
    return progress;
}

// ดึงหรือสร้าง GameProgress
public GameProgress getOrCreateGameProgress(Long userId, String animalType) {
    return gameProgressRepository.findByUserIdAndAnimalType(userId, animalType)
            .orElseGet(() -> {
                GameProgress progress = new GameProgress();
                progress.setUser(userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with ID " + userId)));
                progress.setAnimalType(animalType);
                progress.setCurrentLevel(1);
                return gameProgressRepository.save(progress);
            });
}

// อัปเดตความคืบหน้า
// public GameProgress updateGameProgress(Long userId, String animalType, int level) {
//     GameProgress progress = getOrCreateGameProgress(userId, animalType);

//     if (level > progress.getCurrentLevel()) {
//         progress.setCurrentLevel(level);
//     }

//     return gameProgressRepository.save(progress);
// }

// ปลดล็อกเลเวลถัดไป
public GameProgress unlockNextLevel(Long userId) {
    GameProgress progress = gameProgressRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Game progress not found for user ID " + userId));

    int nextLevel = progress.getCurrentLevel() + 1;
    progress.setCurrentLevel(nextLevel);

    // อัปเดตเหรียญในกรณีที่ถึงระดับสำคัญ
    if (nextLevel == 10) {
        User user = progress.getUser();
        if (!user.getRole().getName().equals("PATIENT")) {
            user.setCoins(user.getCoins() + 1);
            userRepository.save(user);
        }
    }

    return gameProgressRepository.save(progress);
}
//ได้ละอันนี้บันทึกลง
public GameProgress createOrUpdateProgress(Long userId, String animalType, int level, String text) {
    GameProgress progress = gameProgressRepository.findByUserIdAndAnimalType(userId, animalType)
            .orElseGet(() -> {
                GameProgress newProgress = new GameProgress();
                newProgress.setUser(userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with ID " + userId)));
                newProgress.setAnimalType(animalType);
                newProgress.setCurrentLevel(1);
                newProgress.setSymptomNotes(new HashMap<>());
                return newProgress;
            });

    progress.setCurrentLevel(level);
    progress.getSymptomNotes().put(level, text);

    return gameProgressRepository.save(progress);
}

// public GameProgress getGameProgress(Long userId, String animal) {
//     return gameProgressRepository.findByUserIdAndAnimalType(userId, animal)
//             .orElseGet(() -> {
//                 // ตรวจสอบว่าผู้ใช้มีอยู่ในระบบหรือไม่
//                 User user = userRepository.findById(userId)
//                         .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

//                 // สร้าง GameProgress ใหม่
//                 GameProgress newProgress = new GameProgress();
//                 newProgress.setUser(user);
//                 newProgress.setAnimalType(animal);
//                 newProgress.setCurrentLevel(1); // ค่าเริ่มต้น
//                 newProgress.setSymptomNotes(new HashMap<>()); // เริ่มต้นเป็น Map ว่าง

//                 // Logging
//                 System.out.println("Creating new GameProgress for user ID " + userId + " and animal " + animal);

//                 // บันทึกข้อมูลใหม่ลงในฐานข้อมูล
//                 return gameProgressRepository.save(newProgress);
//             });
// }

public GameProgress getGameProgress(Long userId, String animal) {
    return gameProgressRepository.findByUserIdAndAnimalType(userId, animal)
            .orElseGet(() -> {
                GameProgress newProgress = new GameProgress();
                newProgress.setUser(userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with ID " + userId)));
                newProgress.setAnimalType(animal);
                newProgress.setCurrentLevel(1);
                newProgress.setSymptomNotes(new HashMap<>());
                return gameProgressRepository.save(newProgress);
            });
}

public GameProgress updateGameProgress(Long userId, String animal, int level) {
    GameProgress progress = getGameProgress(userId, animal);
    if (level > progress.getCurrentLevel()) {
        progress.setCurrentLevel(level);
    }
    return gameProgressRepository.save(progress);
}

}
