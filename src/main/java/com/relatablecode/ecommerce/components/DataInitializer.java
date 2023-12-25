package com.relatablecode.ecommerce.components;

import com.relatablecode.ecommerce.auth.role.ERole;
import com.relatablecode.ecommerce.auth.role.Role;
import com.relatablecode.ecommerce.auth.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Initialize roles
        Arrays.asList(ERole.values()).forEach(roleEnum -> {
            Role role = roleRepository.findByName(roleEnum)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleEnum);
                        return roleRepository.save(newRole);
                    });
        });
    }
}
