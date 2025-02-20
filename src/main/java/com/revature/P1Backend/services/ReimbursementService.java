package com.revature.P1Backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.UserDataHandler;

import com.revature.P1Backend.DAO.ReimbursementDAO;
import com.revature.P1Backend.DAO.UserDAO;
import com.revature.P1Backend.exceptions.UnauthorizedException;
import com.revature.P1Backend.models.Reimbursement;
import com.revature.P1Backend.models.User;
import com.revature.P1Backend.models.DTOs.IncomingReimbursementDTO;

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
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }

        Reimbursement reimbursement = new Reimbursement(reimbursementDTO);

        Optional<User> reUser = userDAO.findById(reimbursementDTO.getUserId());

        if(reUser.isEmpty()){
            throw new RuntimeException("User defined in reimbursement does not exist!");
        }
        else{
            reimbursement.setUser(reUser.get());
        }

        return reimbursementDAO.save(new Reimbursement());
    }

    //Employee functionality
    public List<Reimbursement> getReimbursementsByUser(User currentUser) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        return reimbursementDAO.getReimbursementsByUser(currentUser.getUserId());
    }

    //Employee functionality
    public List<Reimbursement> getPendingReimbursementsByUser(User currentUser) {
        if(currentUser == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(currentUser.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }
        return reimbursementDAO.getPendingReimbursementsByUser(currentUser.getUserId());
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
        return reimbursementDAO.findReimbursementByStatus("PENDING");
    }

    public Reimbursement resolveReimbursement(int id, String decisionString, User currentUser) {
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            throw new UnauthorizedException("You are currently not authorized to perform this action.");
        }
        if(decisionString == null)
            return null;
        else{
            Reimbursement resolvedReimbursement = reimbursementDAO.findReimbursementByReimbursementId(id);
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
            Reimbursement updatedReimbursement = reimbursementDAO.findReimbursementByReimbursementId(id);
            updatedReimbursement.setDescription(newDescription);
            return reimbursementDAO.save(updatedReimbursement);
        }
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