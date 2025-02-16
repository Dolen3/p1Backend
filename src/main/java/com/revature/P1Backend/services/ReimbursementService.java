package com.revature.P1Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.P1Backend.DAO.ReimbursementDAO;
import com.revature.P1Backend.exceptions.UnauthorizedException;
import com.revature.P1Backend.models.Reimbursement;
import com.revature.P1Backend.models.User;

public class ReimbursementService {

    @Autowired
    private ReimbursementDAO reimbursementDAO;

    //Employee functionality only
    public Reimbursement createReimbursement(User user, Reimbursement reimbursement) {
        if(user == null){
            throw new UnauthorizedException("You must be logged in to perform this action.");
        }
        if("MANAGER".equals(user.getRole())){
            throw new UnauthorizedException("You do not have permission to perform this action.");
        }

        return reimbursementDAO.save(reimbursement);
    }

    public List<Reimbursement> getReimbursementsByUser(User user) {
        return reimbursementDAO.getReimbursementsByUser(user.getUserId());
    }

    public List<Reimbursement> getPendingReimbursementsByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPendingReimbursementsByUser'");
    }

    public List<Reimbursement> getAllReimbursements(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllReimbursements'");
    }

    public List<Reimbursement> getAllPendingReimbursements(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPendingReimbursements'");
    }

    public Reimbursement resolveReimbursement(int id, String decisionString, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resolveReimbursement'");
    }

    public Reimbursement updateReimbursement(int id, User user, String newDescription) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateReimbursement'");
    }
}
