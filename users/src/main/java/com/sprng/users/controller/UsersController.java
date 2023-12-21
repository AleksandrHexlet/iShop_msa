package com.sprng.users.controller;

import com.sprng.library.entity.ProductTrader;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/exists/id/{id}/username/{username}/role/{role}")
    public Boolean existsUserByIdAndUserNameAndRole(@PathVariable int id,
                                                    @PathVariable String username,
                                                    @PathVariable String role) {
        return usersService.existsUserByIdAndUserNameAndRole(id, username, role);
    }

//    @GetMapping("/exists/customer/{customerId}/trader/{traderId}")


}