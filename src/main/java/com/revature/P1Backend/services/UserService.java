package com.revature.P1Backend.services;

import com.revature.P1Backend.DAO.UserDAO;
import com.revature.P1Backend.exceptions.ResourceNotFoundException;
import com.revature.P1Backend.exceptions.UnauthorizedException;
import com.revature.P1Backend.models.User;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.AccessDeniedException;
import java.util.List;

public class UserService {
    @Autowired
    private UserDAO userDAO;

    //Employee Functionality
    public User createUser(User user){
        user.setRole("EMPLOYEE"); //Default role
        return userDAO.save(user);
    }


    public User getUserById(int id, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in.");
        }

        //A manager can see anyone they want
        if ("MANAGER".equals(currentUser.getRole())) {
            return userDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        //Ammployee can see themselves
        if ("EMPLOYEE".equals(currentUser.getRole()) && currentUser.getUserId() == id) {
            return userDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        throw new UnauthorizedException("You don't have permission to view other users' data.");
    }

    //only manager can get all users
    public List<User> getAllUsers(User currentUser){
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("Only managers can view all users.");
        }
        return userDAO.findAll();
    }

    //Only manager can delete user
    public void deleteUser(int id, User currentUser){
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("Only managers can view all users.");
        }
        userDAO.deleteById(id);
    }

    //Only manager can update a role
    public User updateUserRole(int userId, String newRole, User currentUser) {
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("Only managers can update user roles.");
        }

        User userToUpdate = userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userToUpdate.setRole(newRole);
        return userDAO.save(userToUpdate);
    }
}
