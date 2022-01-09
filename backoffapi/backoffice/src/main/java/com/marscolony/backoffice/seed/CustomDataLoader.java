package com.marscolony.backoffice.seed;

import com.marscolony.backoffice.entities.Role;
import com.marscolony.backoffice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomDataLoader implements CommandLineRunner {

    //@Value("${app.start-up.seed}")
    //boolean seedUnits = false;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {

        try {

            if (roleRepository.count() == 0) {

                System.out.println("Let's seed the database for the first time");

                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
                Role userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);

            }

        } catch (Exception e) {
            System.out.println("Exception at loadUserData: " + e.getMessage());
        }


    }
}
