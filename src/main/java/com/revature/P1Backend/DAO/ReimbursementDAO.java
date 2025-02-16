package com.revature.P1Backend.DAO;

import com.revature.P1Backend.models.Reimbursement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

    @Query("FROM Reimbursement WHERE createdBy = :idVar")
    List<Reimbursement> getReimbursementsByUser(@Param("idVar") int userId);

    
} 
