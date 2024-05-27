package com.example.pfa.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Filiere;
import com.example.pfa.entities.FiliereSemestre;
import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.Module;
import com.example.pfa.entities.PrepaStudent;
import com.example.pfa.entities.Professor;
import com.example.pfa.entities.Role;
import com.example.pfa.entities.Semestre;
import com.example.pfa.repositories.FiliereRepository;
import com.example.pfa.repositories.FiliereSemestreRepository;
import com.example.pfa.repositories.IngStudentRepository;
import com.example.pfa.repositories.PrepaStudentRepository;
import com.example.pfa.repositories.ProfessorRepository;
import com.example.pfa.repositories.RoleRepository;
import com.example.pfa.repositories.SemestreRepository;
import com.github.javafaker.Faker;

@Service
public class DataStarter {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FiliereRepository filiereRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SemestreRepository semestreRepository;

    @Autowired
    FiliereSemestreRepository filiereSemestreRepository;

    @Autowired
    IngStudentRepository ingStudentRepository;

    @Autowired
    PrepaStudentRepository prepaStudentRepository;

    public void createProfessors() {

        Role professorRole = Role.builder().name("ROLE_PROFESSOR").build();
        professorRole = roleRepository.save(professorRole);


        for (int i = 0; i < 40; i++) {

            Faker faker = new Faker();

            // Generate fake data using Faker
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = passwordEncoder.encode("1234");
            String telephone = faker.phoneNumber().cellPhone();
            boolean isEnabled = true; // Faker doesn't provide direct support for generating booleans

            Professor newProfessor = new Professor();

            newProfessor.setCreatedAt(LocalDate.now());
            newProfessor.setDateNaissance(LocalDate.now());
            newProfessor.setEmail(email);
            newProfessor.setUsername(username);
            newProfessor.setEnabled(isEnabled);
            newProfessor.setFirstName(firstName);
            newProfessor.setLastName(lastName);
            newProfessor.setLieuNaissance("Casablanca");
            newProfessor.setPassword(password);
            newProfessor.setRoles(Set.of(professorRole));
            newProfessor.setTelephone(telephone);
            newProfessor.setUpdatedAt(LocalDate.now());
            // Save the new professor
            professorRepository.save(newProfessor);

        }
    }

    // Create the Filieres

    public void createFilieres() {
        // Create filieres
        var gee = Filiere.builder()
                .description("Génie énergétique et électrique")
                .name("G2E")
                .build();

        var gi = Filiere.builder()
                .description("Génie Industriel")
                .name("GI")
                .build();

        var gc = Filiere.builder()
                .description("Génie civil")
                .name("GC")
                .build();

        gee = filiereRepository.save(gee);
        gi = filiereRepository.save(gi);
        gc = filiereRepository.save(gc);

        // Create semesters
        Semestre s1 = new Semestre();
        s1.setCount(1);

        Semestre s2 = new Semestre();
        s2.setCount(2);

        Semestre s3 = new Semestre();
        s3.setCount(3);

        Semestre s4 = new Semestre();
        s4.setCount(4);

        Semestre s5 = new Semestre();
        s5.setCount(5);

        s1 = semestreRepository.save(s1);
        s2 = semestreRepository.save(s2);
        s3 = semestreRepository.save(s3);
        s4 = semestreRepository.save(s4);
        s5 = semestreRepository.save(s5);

        // Create FiliereSemestre for each combination of filiere and semester
        createFiliereSemestre(gee, s1);
        createFiliereSemestre(gi, s1);
        createFiliereSemestre(gc, s1);

        createFiliereSemestre(gee, s2);
        createFiliereSemestre(gi, s2);
        createFiliereSemestre(gc, s2);

        createFiliereSemestre(gee, s3);
        createFiliereSemestre(gi, s3);
        createFiliereSemestre(gc, s3);

        createFiliereSemestre(gee, s4);
        createFiliereSemestre(gi, s4);
        createFiliereSemestre(gc, s4);

        createFiliereSemestre(gee, s5);
        createFiliereSemestre(gi, s5);
        createFiliereSemestre(gc, s5);

        Faker faker = new Faker();

        // Assuming premierSemestreIITE is defined somewhere in your code
        // Role etudiantRole = roleRepository.getById(1L);
        Role etudiantRole = Role.builder().name("ROLE_ETUDIANT").build();
        etudiantRole = roleRepository.save(etudiantRole);

        for (FiliereSemestre filiereSemestre : filiereSemestreRepository.findAll()) {
            // Skip S2 and S4
            if (filiereSemestre.getSemestre().getCount() == 2 || filiereSemestre.getSemestre().getCount() == 4) {
                continue;
            }
            
            for (int i = 0; i < 35; i++) {
                IngStudent ing = new IngStudent();
                ing.setFirstName(faker.name().firstName());
                ing.setLastName(faker.name().lastName());
                ing.setUsername(faker.name().username());
                ing.setEmail(faker.internet().emailAddress());
                ing.setPassword(passwordEncoder.encode("123"));
                ing.setTelephone(faker.phoneNumber().cellPhone());
                ing.setEnabled(true);
                ing.setCreatedAt(LocalDate.now());
                ing.setUpdatedAt(LocalDate.now());
                ing.setLieuNaissance(faker.address().cityName());
                ing.setDateNaissance(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                ing.setCne(faker.idNumber().valid());
                ing.setCin(faker.idNumber().valid());
                ing.setRoles(Set.of(etudiantRole));

                // Set the FiliereSemestre for the student
                ing.setFiliereSemestre(filiereSemestre);

                // Save the IngStudent
                ingStudentRepository.save(ing);
            }
        }

        // Creating prepa Student

        List<Semestre> semestres = semestreRepository.findAll();

        for (int i = 0; i < Math.min(4, semestres.size()); i++) {

            Semestre semestre = semestres.get(i);
            if (semestre.getCount() == 1 || semestre.getCount() == 3) {

                for (int j = 0; j < 20; j++) {
                    PrepaStudent prepa = new PrepaStudent();
                    prepa.setFirstName(faker.name().firstName());
                    prepa.setLastName(faker.name().lastName());
                    prepa.setUsername(faker.name().username());
                    prepa.setEmail(faker.internet().emailAddress());
                    prepa.setPassword(passwordEncoder.encode(faker.internet().password()));
                    prepa.setTelephone(faker.phoneNumber().cellPhone());
                    prepa.setEnabled(true);
                    prepa.setCreatedAt(LocalDate.now());
                    prepa.setUpdatedAt(LocalDate.now());
                    prepa.setLieuNaissance(faker.address().cityName());
                    prepa.setDateNaissance(
                            faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    prepa.setCne(faker.idNumber().valid());
                    prepa.setCin(faker.idNumber().valid());
                    prepa.setSemestre(semestre);

                    // Save the prepaStudent
                    prepaStudentRepository.save(prepa);
                }
            }
        }

    }

    private void createFiliereSemestre(Filiere filiere, Semestre semestre) {
        FiliereSemestre filiereSemestre = new FiliereSemestre();

        filiereSemestre.setFiliere(filiere);
        filiereSemestre.setSemestre(semestre);
        filiereSemestreRepository.save(filiereSemestre);
    }

    // Creating the modules
    public void createModules() {

        List<String> modules = Arrays.asList("Mathématiques de l'ingénieur",
                "Electronique analogique et capteurs ",
                "Circuits électriques et magnétiques ",
                "Thermodynamique Appliquée ",
                "Traitement de signal ",
                "Phénomènes de Transfert ",
                " Management des organisations ",
                "Langues et communication I ",
                "Statistiques, Optimisation et Recherche Opérationnelle",
                "Programmation Orientée Objet – C++ et Réseaux",
                "Machines électriques",
                "Automatisme & système asservis",
                "Mécanique des Fluides et machines hydrauliques",
                "Management fonctionnel",
                "Langues et communication II",
                "Commande et supervision des installations industrielles",
                "Energie Nucléaire et stockage d’énergie",
                "Froid industriel et Matériaux pour l'energétique",
                "Réseaux et protections électriques",
                "Informatique industrielle et capteurs embarqués",
                "Electronique de puissance",
                "Management de la maintenance et de la qualité",
                "Langues et communication III ",
                "Production de l'Energie Electrique d'origines renouvelables ",
                "Gestion et analyse de projets ",
                "Outils d'aide à la décision et techniques de créativité");

        Module module = new Module();
    }
}
