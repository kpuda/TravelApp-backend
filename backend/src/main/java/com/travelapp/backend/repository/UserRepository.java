package com.travelapp.backend.repository;

import com.travelapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameOrEmail(String username, String email);

    @Query(value = "SELECT username from User")
    List<String> getUsernames();
    User findByUsername(String username);

    User findByEmail(String email);

}
