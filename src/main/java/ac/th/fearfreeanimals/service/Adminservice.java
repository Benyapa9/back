package ac.th.fearfreeanimals.service;

import ac.th.fearfreeanimals.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.th.fearfreeanimals.entity.*;

@Service
public class Adminservice {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createDoctor(String username, String password) {
        // ค้นหา Role "DOCTOR"
        Role doctorRole = roleRepository.findByName("DOCTOR")
                .orElseThrow(() -> new RuntimeException("Role DOCTOR not found"));

        // สร้างบัญชี Doctor ใหม่
        User doctor = new User();
        doctor.setUsername(username);
        doctor.setPassword(password); // ควรเข้ารหัสด้วย BCrypt
        doctor.setRole(doctorRole);
        doctor.setIsDoctor(true);

        // บันทึกในฐานข้อมูล
        return userRepository.save(doctor);
    }
}
