package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.service.Adminservice;
import ac.th.fearfreeanimals.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ac.th.fearfreeanimals.dto.UserRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private Adminservice adminService;

    @PostMapping("/add-doctor")
    public ResponseEntity<User> addDoctor(@RequestBody UserRequest userRequest) {
        User doctor = adminService.createDoctor(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.ok(doctor);
    }
}


