package com.demo.spring.AuthorizationServerApplication.repository;

import com.demo.spring.AuthorizationServerApplication.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
}
