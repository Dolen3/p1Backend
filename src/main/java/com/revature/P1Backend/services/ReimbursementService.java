package com.revature.P1Backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

import com.revature.P1Backend.DAO.ReimbursementDAO;
import com.revature.P1Backend.DAO.UserDAO;
import com.revature.P1Backend.exceptions.UnauthorizedException;
import com.revature.P1Backend.models.Reimbursement;
import com.revature.P1Backend.models.User;
import com.revature.P1Backend.models.DTOs.IncomingReimbursementDTO;

@Service
public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO;
    private UserDAO userDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbursementDAO, UserDAO userDAO){
        this.reimbursementDAO = reimbursementDAO;
        this.userDAO = userDAO;
    }



    //Employee functionality only
    public Reimbursement createReimbursement(User currentUser, IncomingReimbursementDTO reimbursementDTO) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if ("MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }

        System.out.println("User ID from DTO: " + reimbursementDTO.getUserId()); // Debug log
        Reimbursement reimbursement = new Reimbursement(reimbursementDTO);

        Optional<User> reUser = userDAO.findById(reimbursementDTO.getUserId());
        if (reUser.isEmpty()) {
            throw new RuntimeException("User with ID " + reimbursementDTO.getUserId() + " does not exist!");
        } else {
            reimbursement.setUser(reUser.get());
        }

        return reimbursementDAO.save(reimbursement);
    }

    //Employee functionality
    public List<Reimbursement> getReimbursementsByUser(User currentUser) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        return reimbursementDAO.findReimbursementsByUser_UserId(currentUser.getUserId());
    }

    //Employee functionality
    public List<Reimbursement> getPendingReimbursementsByUser(User currentUser) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        return reimbursementDAO.findReimbursementsByStatusAndUser_UserId("PENDING", currentUser.getUserId());
    }

    //Manager functionality
    public List<Reimbursement> getAllReimbursements(User currentUser) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if(!"MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        return reimbursementDAO.findAll();
    }

    public List<Reimbursement> getAllPendingReimbursements(User currentUser) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if(!"MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        return reimbursementDAO.findReimbursementsByStatus("PENDING");
    }

    public Reimbursement resolveReimbursement(int id, String decisionString, User currentUser) {
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("You are currently not authorized to perform this action.");
        }
        if(decisionString == null)
            return null;
        else{
            Reimbursement resolvedReimbursement = reimbursementDAO.findByReimbursementId(id);
            resolvedReimbursement.setStatus(decisionString);
            return reimbursementDAO.save(resolvedReimbursement);
        }
    }

    public Reimbursement updateReimbursement(int id, User currentUser, String newDescription) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        else{
            Reimbursement updatedReimbursement = reimbursementDAO.findByReimbursementId(id);
            updatedReimbursement.setDescription(newDescription);
            return reimbursementDAO.save(updatedReimbursement);
        }
    }

    // New method using ReimbursementDAO
    public List<Reimbursement> getReimbursementsByUserId(int userId) {
        // Check if the user exists (optional, depending on your requirements)
        Optional<User> optionalUser = userDAO.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        // Use the DAO method to fetch reimbursements by user ID
        List<Reimbursement> reimbursements = reimbursementDAO.findReimbursementsByUser_UserId(userId);
        if (reimbursements.isEmpty()) {
            throw new RuntimeException("No reimbursements found for user with ID: " + userId);
        }
        return reimbursements;
    }

    /*private boolean authorize(User user, String neededRole){
        if(user.getRole().equals(neededRole)){
            return true;
        }
        else{
            throw new Exception;
            return false;
        }

    }*/
}