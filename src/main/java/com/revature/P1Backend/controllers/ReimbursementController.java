package com.revature.P1Backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.P1Backend.models.Reimbursement;
import com.revature.P1Backend.models.User;
import com.revature.P1Backend.models.DTOs.IncomingReimbursementDTO;
import com.revature.P1Backend.services.ReimbursementService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class ReimbursementController {

    @Autowired
    private ReimbursementService reimbursementService;

    @PostMapping("/create")
    public ResponseEntity <Reimbursement> createReimbursement(@RequestBody IncomingReimbursementDTO reimbursementDTO, HttpSession session){
        User user = (User) session.getAttribute("currentUser");

        return ResponseEntity.accepted().body(reimbursementService.createReimbursement(user, reimbursementDTO));
    }

    //Employees can see all of their reimbursements
    @GetMapping 
    public ResponseEntity<List<Reimbursement>> getReimbursementsByUser(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.getReimbursementsByUser(user));
    }

    //Employees can see all of their pending reimbursements
    @GetMapping ("/pending")
    public ResponseEntity<List<Reimbursement>>  getPendingReimbursementsByUser(HttpSession session, @PathVariable int id){
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.getPendingReimbursementsByUser(user));
    }

    //Managers can see all reimbursements under all users, employees are unauthorized
    @GetMapping("/management")
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.getAllReimbursements(user));
    }

    //Managers can see all pending reimbursements under all users
    @GetMapping ("management/pending")
    public ResponseEntity<List<Reimbursement>> getAllPendingReimbursements(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.getAllPendingReimbursements(user));
    }

    //Managers can update the status of a reimbursement to approved or denied
    @PutMapping("/{id}/updateReimbursement")
    public ResponseEntity<Reimbursement> updateReimbursement(@PathVariable int id, @RequestBody String decisionString, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.resolveReimbursement(id, decisionString, user));
    }

    //Employees can update the description of a reimbursement
    @PutMapping("/{id}/updateDescription")
    public ResponseEntity<Reimbursement> updateReimbursementDescription(@PathVariable int id, @RequestBody String newDescription, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.updateReimbursement(id, user, newDescription));
    }

    // New endpoint: Managers can view reimbursements for a specific user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsById( @PathVariable int userId, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        return ResponseEntity.ok(reimbursementService.getReimbursementsByUserId(userId));
    }
}
