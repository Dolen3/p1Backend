package com.revature.P1Backend.services;

import com.revature.P1Backend.DAO.UserDAO;
import com.revature.P1Backend.exceptions.ResourceNotFoundException;
import com.revature.P1Backend.exceptions.UnauthorizedException;
import com.revature.P1Backend.models.DTOs.OutgoingUserDTO;
import com.revature.P1Backend.models.User;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    //Employee Functionality
    public OutgoingUserDTO createUser(User user){
        user.setRole("EMPLOYEE"); // Default role
        User savedUser = userDAO.save(user);
        return new OutgoingUserDTO(savedUser);
    }


    public OutgoingUserDTO getUserById(int id, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in.");
        }

        User user = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if ("MANAGER".equals(currentUser.getRole()) ||
                ("EMPLOYEE".equals(currentUser.getRole()) && currentUser.getUserId() == id)) {
            return new OutgoingUserDTO(user);
        }

        throw new UnauthorizedException("You don't have permission to view other users' data.");
    }

    //only manager can get all users
    public List<OutgoingUserDTO> getAllUsers(User currentUser){
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("Only managers can view all users.");
        }
        List<User> returnedUsers = userDAO.findAll();
        List<OutgoingUserDTO> userDTOs = new ArrayList<>();
        for (User u : returnedUsers) {
            userDTOs.add(new OutgoingUserDTO(u));
        }

        return userDTOs;
    }

    // Only manager can delete user
    public void deleteUser(int id, User currentUser){
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("Only managers can delete users.");
        }
        userDAO.deleteById(id);
    }

    // Only manager can update a role
    public OutgoingUserDTO updateUserRole(int userId, String newRole, User currentUser) {
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("Only managers can update user roles.");
        }

        User userToUpdate = userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userToUpdate.setRole(newRole);
        User updatedUser = userDAO.save(userToUpdate);
        return new OutgoingUserDTO(updatedUser);
    }
}
