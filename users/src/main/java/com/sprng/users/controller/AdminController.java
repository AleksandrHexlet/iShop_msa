package com.sprng.users.controller;


import com.sprng.library.entity.AdminIshop;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping
    public ResponseEntity<AdminIshop> getAdminByUserName(String username){
        try{
            AdminIshop adminIshop= adminService.getAdminByUserName(username);
            return new ResponseEntity<AdminIshop>(adminIshop, HttpStatus.FOUND);
        }catch (IshopResponseException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  exception.getMessage());
        }
    }

    @GetMapping
    public String getAdmin1(String username){
 return " this is answer = " + username;
    }


}