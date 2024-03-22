package com.artic.artic_doctors;

import com.artic.artic_doctors.Entities.Admin;
import com.artic.artic_doctors.Entities.Role;
import com.artic.artic_doctors.Entities.User;
import com.artic.artic_doctors.Repository.IAdminRepository;
import com.artic.artic_doctors.Repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@ComponentScan(basePackages={"com.artic.artic_doctors" ,"com.artic.artic_doctors.Cors"})
public class ArticDoctorsApplication implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IAdminRepository adminRepository;
    public static void main(String[] args) {
        SpringApplication.run(ArticDoctorsApplication.class, args);
    }
    public void run(String... args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            Admin admin = new Admin();
            admin.setEmail("admin@gmail.com");
            admin.setNom("admin");
            admin.setPrenom("admin");
            admin.setRole(Role.ADMIN);

            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            adminRepository.save(admin);
        }
    }
}
