package com.revature.P1Backend.models.DTOs;


import com.revature.P1Backend.models.User;

public class OutgoingUserDTO {

    private int userId;
    private String username;
    private String role;

    public OutgoingUserDTO() {
    }

    public OutgoingUserDTO(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public OutgoingUserDTO(User u) {
        this.userId = u.getUserId();
        this.username = u.getUsername();
        this.role = u.getRole();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}