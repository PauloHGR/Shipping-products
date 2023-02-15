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

    @GetMapping("/find")
    public Users getUserByNameAndEmail(@RequestParam("name") String name, @RequestParam("email") String email){
        return userDAO.findByNameAndEmail(name,email);
    }

    @GetMapping("{id}")
    public Users getUserByNameAndEmail(@PathVariable Long id){
        return userDAO.findById(id);
    }

    @PostMapping
    public void saveUser(@RequestBody Users user){
        userDAO.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        Users user = userDAO.findById(id);
        userDAO.delete(user);
    }
}
