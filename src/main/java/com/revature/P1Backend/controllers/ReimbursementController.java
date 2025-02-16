package com.revature.P1Backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.revature.P1Backend.services.ReimbursementService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {

    @Autowired
    private ReimbursementService reimbursementService;

    @PostMapping("/create")
    public Reimbursement createReimbursement(@RequestBody Reimbursement reimbursement, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.createReimbursement(user, reimbursement);
    }

    @GetMapping 
    public List<Reimbursement> getReimbursementsByUser(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.getReimbursementsByUser(user);
    }

    @GetMapping ("/pending")
    public List<Reimbursement>  getPendingReimbursementsByUser(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.getPendingReimbursementsByUser(user);
    }

    @GetMapping
    public List<Reimbursement> getAllReimbursements(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.getAllReimbursements(user);
    }

    @GetMapping
    public List<Reimbursement> getAllPendingReimbursements(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.getAllPendingReimbursements(user);
    }

    @DeleteMapping("/{id}/resolve")
    public Reimbursement deleteReimbursement(@PathVariable int id, @RequestBody String decisionString, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.resolveReimbursement(id, decisionString, user);
    }

    @PutMapping("{id}/update")
    public Reimbursement updateReimbursement(@PathVariable int id, @RequestBody String newDescription, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return reimbursementService.updateReimbursement(id, user, newDescription);
    }
}
