package com.revature.P1Backend.DAO;

import com.revature.P1Backend.models.Reimbursement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

    public List<Reimbursement> findReimbursementsByUser_UserId(int userId);

    public List<Reimbursement> findReimbursementsByStatusAndUser_UserId(String status, int userId);

    public List<Reimbursement> findReimbursementsByStatus(String status);

    public Reimbursement findByReimbursementId(int id);
} 
