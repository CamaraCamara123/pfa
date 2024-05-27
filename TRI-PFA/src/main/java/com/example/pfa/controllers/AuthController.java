package com.example.pfa.controllers;
import com.example.pfa.dto.AuthenticationRequest;
import com.example.pfa.dto.ErrorMessage;
import com.example.pfa.dto.SuccesMessage;
import com.example.pfa.dto.TokenResponse;
import com.example.pfa.entities.Role;
import com.example.pfa.entities.User;
import com.example.pfa.services.AuthenticationService;
import com.example.pfa.services.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UserInfo {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String telephone;
    private Set<Role> roles;

}

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LogoutService logoutService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            System.out.println(request);
            TokenResponse jwtToken = authenticationService.authenticate(request);
            System.out.println("Logged IN");
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        try {
            String message = this.logoutService.doLogout(request);
            return ResponseEntity.ok(new SuccesMessage(message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/userinfo")
    public UserInfo userinfo(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        // user.setTodos(null);
        return UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

}
