package com.thanhtrung.loginsecurity.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanhtrung.loginsecurity.dto.UserDTO;
import com.thanhtrung.loginsecurity.service.IUserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/register")
@Validated
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping()
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO dto) {

		// create User
		userService.createUser(dto.toEntity());

		return new ResponseEntity<>("We have sent 1 email. Please check email to active account!", HttpStatus.OK);
	}

	@GetMapping("/activeUser")
	public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {

		// active user
		userService.activeUser(token);

		return new ResponseEntity<>("Active success!", HttpStatus.OK);
	}


	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}
}
