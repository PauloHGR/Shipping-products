package org.com.shipment.controller;

import org.com.shipment.model.Users;
import org.com.shipment.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private UserDAO userDAO;

    @GetMapping
    public List<Users> getAllUsers(){
        return userDAO.getUsers();
    }
}
