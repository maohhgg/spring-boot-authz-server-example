package com.demo.spring.AuthorizationServerApplication.controller;


import com.demo.spring.AuthorizationServerApplication.entity.Users;
import com.demo.spring.AuthorizationServerApplication.service.CustomUsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class UserController {

    @Autowired
    private CustomUsersDetailsService customUsersDetailsService;

    @GetMapping("/api/user")
    @ResponseBody
    public ResponseEntity<Users> userInfo(Principal principal) {
        Users users = customUsersDetailsService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(users);
    }

}
