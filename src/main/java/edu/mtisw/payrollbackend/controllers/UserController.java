package edu.mtisw.payrollbackend.controllers;

import edu.mtisw.payrollbackend.entities.UserEntity;
import edu.mtisw.payrollbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
		return ResponseEntity.ok(userService.register(user.getRut(),
									user.getName(),
									user.getEmail(),
									user.getPassword(),
									user.getRole()));
	}

	@PostMapping("/login")
	public ResponseEntity<UserEntity> login(@RequestBody UserEntity user) {
		return ResponseEntity.ok(userService.login(user.getRut(), user.getPassword()));
	}
}
