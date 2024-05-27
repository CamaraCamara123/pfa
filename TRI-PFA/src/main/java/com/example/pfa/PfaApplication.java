package com.example.pfa;

import com.example.pfa.entities.*;
import com.example.pfa.repositories.*;
import com.example.pfa.utils.DataStarter;
import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PfaApplication {

    @Autowired
    DataStarter dataStarter;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    FiliereRepository filiereRepository;
    @Autowired
    SemestreRepository semestreRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    FiliereSemestreRepository filiereSemestreRepository;

    public static void main(String[] args) {
        SpringApplication.run(PfaApplication.class, args);
    }

    // @Bean
    CommandLineRunner init() {
        return args -> {
            Role adminRole = Role.builder().name("ROLE_ADMIN").build();
            Role etudiantRole = Role.builder().name("ROLE_ETUDIANT").build();

            etudiantRole = roleRepository.save(etudiantRole);
            adminRole = roleRepository.save(adminRole);
            var admin = User.builder()
                    .firstName("admin")
                    .lastName("nouri")
                    .username("admin").email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(adminRole))
                    .telephone("0665117843")
                    .isEnabled(true)
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build();

            userRepository.save(admin);

            // Creating the professors
            dataStarter.createProfessors();

            dataStarter.createFilieres();

        };
    }
}
