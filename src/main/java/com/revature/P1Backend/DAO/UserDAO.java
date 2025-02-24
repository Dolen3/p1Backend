package com.revature.P1Backend.DAO;

import com.revature.P1Backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Extending JpaRepository gives us access to a bunch of CRUD methods.
//The Jpa Repository takes 2 generics:
//The entity (user) and the primary key (Integer: userId)

@Repository //1 of the 4 Stereotype annotaion
public interface UserDAO extends JpaRepository<User, Integer> {
    public Optional<User> findByUsernameAndPassword(String username, String password);
    public Optional<User> findByUsername(String username);
}
