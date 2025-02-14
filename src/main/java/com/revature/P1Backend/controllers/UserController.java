package com.revature.P1Backend.controllers;
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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);

    @PostMapping
    public User createUser(@RequestBody User user) {
        // The service sets role = "EMPLOYEE" by default.
        return userService.createUser(user);

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return userService.getUserById(id, currentUser);
    }

    @GetMapping
    public List<User> getAllUsers(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return userService.getAllUsers(currentUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        userService.deleteUser(id, currentUser);
    }

    @PutMapping("/{id}/role")
    public User updateUserRole(@PathVariable int id, @RequestBody String newRole, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return userService.updateUserRole(id, newRole, currentUser);
    }

}
