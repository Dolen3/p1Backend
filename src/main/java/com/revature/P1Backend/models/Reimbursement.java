package com.revature.P1Backend.models;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    

    private User createdBy; //maps to user

    public Reimbursement(){
    }

    public Reimbursement(int reimbursementId, int createdBy){

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
}
