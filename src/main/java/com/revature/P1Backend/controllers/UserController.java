package com.revature.P1Backend.controllers;
import com.revature.P1Backend.models.DTOs.LoginDTO;
import com.revature.P1Backend.models.DTOs.OutgoingUserDTO;
import com.revature.P1Backend.models.User;
import com.revature.P1Backend.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<OutgoingUserDTO> createUser(@RequestBody User user) {
        OutgoingUserDTO createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<OutgoingUserDTO> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        OutgoingUserDTO loggedInUser = userService.login(loginDTO);
        User user = new User(loggedInUser.getUserId(), loggedInUser.getUsername(), null, loggedInUser.getRole());
        session.setAttribute("currentUser", user);
        System.out.println("User " + session.getAttribute("username") + " has logged in!");
        return ResponseEntity.ok(loggedInUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingUserDTO> getUserById(@PathVariable int id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(userService.getUserById(id, currentUser));
    }

    @GetMapping
    public ResponseEntity<List<OutgoingUserDTO>> getAllUsers(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(userService.getAllUsers(currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        userService.deleteUser(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<OutgoingUserDTO> updateUserRole(@PathVariable int id, @RequestBody String newRole, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(userService.updateUserRole(id, newRole, currentUser));
    }
}