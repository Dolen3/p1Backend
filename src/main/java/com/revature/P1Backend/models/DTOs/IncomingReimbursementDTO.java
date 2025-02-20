package com.revature.P1Backend.models.DTOs;

public class IncomingReimbursementDTO {

    private String description;
    private int userId;

    public IncomingReimbursementDTO(String description, int userId) {
        this.description = description;
        this.userId = userId;
    }

    public IncomingReimbursementDTO(){
        
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return "IncominReimbursementDTO{" +
        "description='" + description +'\'' +
        ", userId='" + userId + '}';
    }
}
