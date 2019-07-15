package com.demo.spring.AuthorizationServerApplication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/api/users/me")
    public ResponseEntity<UserProfile> profile()
    {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String name = loggedInUser.getName();

        UserProfile profile = new UserProfile();
        profile.setName(name);
        profile.setEmail(name + "@localhost");

        return ResponseEntity.ok(profile);
    }

}
