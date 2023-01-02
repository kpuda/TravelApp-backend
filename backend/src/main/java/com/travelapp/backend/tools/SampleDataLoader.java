package com.travelapp.backend.tools;

import com.github.javafaker.Faker;
import com.travelapp.backend.entity.Address;
import com.travelapp.backend.entity.Role;
import com.travelapp.backend.entity.User;
import com.travelapp.backend.enums.UserRole;
import com.travelapp.backend.repository.RoleRepository;
import com.travelapp.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SampleDataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final Faker faker = new Faker();


    @PostConstruct
    void run() {
  /*      List<Role> roles = List.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN"), new Role("ROLE_MOD"));
        roleRepository.saveAll(roles);
*/
        User normalUser = new User("username", "Marie", "Johnson", "email@email", passwordEncoder.encode("password"), Set.of(new Role("ROLE_USER")));
        normalUser.setEnabled(true);
        userRepository.save(normalUser);

        User admin = new User("admin", "admin@admin", passwordEncoder.encode("password"), Set.of(new Role(UserRole.ROLE_ADMIN.name())));
        admin.setEnabled(true);
        userRepository.save(admin);
    }
}
