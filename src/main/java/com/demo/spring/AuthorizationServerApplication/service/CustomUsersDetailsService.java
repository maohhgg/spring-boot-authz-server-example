package com.demo.spring.AuthorizationServerApplication.service;

import com.demo.spring.AuthorizationServerApplication.entity.Users;
import com.demo.spring.AuthorizationServerApplication.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    private UsersRepository usersRepository;

    @Autowired
    public CustomUsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository
                .findByUsername(username)
                .map(users -> new Users(users.getUsername(), users.getPassword(), users.getEmail()))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }
}
