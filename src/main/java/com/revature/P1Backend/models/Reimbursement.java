package com.revature.P1Backend.models;

import org.springframework.stereotype.Component;

import com.revature.P1Backend.models.DTOs.IncomingReimbursementDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import jakarta.persistence.Entity;

@Component
@Entity
@Table(name = "reimbursements")
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbursementId;

    

    @Column
    private String status;
    private String description;

    private User user; //maps to user

    public Reimbursement(){
    }

    public Reimbursement(IncomingReimbursementDTO reimbursementDTO){
        this.status = "PENDING";
        this.description = reimbursementDTO.getDescription();
        reimbursementId = 0;
        user = null;
    }

    public Reimbursement(int reimbursementId, User user, String status, String description){
        this.reimbursementId = reimbursementId;
        this.user = user;
        this.status = status;
        this.description = description;
    }

    public int getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public String getStatus(){
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
