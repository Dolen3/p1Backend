package com.revature.P1Backend.controllers;
import com.revature.P1Backend.models.DTOs.OutgoingUserDTO;
import com.revature.P1Backend.models.User;
import com.revature.P1Backend.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //WE NEED HttpSession to get the current user!! Could have been done other ways


    @PostMapping("/register")
    public ResponseEntity<OutgoingUserDTO> createUser(@RequestBody User user) {
        OutgoingUserDTO createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
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
