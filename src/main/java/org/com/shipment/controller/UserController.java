package org.com.shipment.controller;

import org.com.shipment.model.Users;
import org.com.shipment.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    public List<Users> getAllUsers(){
        return userDAO.getUsers();
    }

    @GetMapping("{name}/{email}")
    public Users getUserByNameAndEmail(@PathVariable String name, @PathVariable String email){
        return userDAO.findByNameAndEmail(name,email);
    }

    @PostMapping
    public void saveUser(@RequestBody Users user){
        userDAO.save(user);
        System.out.println("Salvo!!");
    }
}
