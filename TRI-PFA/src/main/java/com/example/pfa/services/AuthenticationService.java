package com.example.pfa.services;
import com.example.pfa.dto.AuthenticationRequest;
import com.example.pfa.dto.TokenResponse;
import com.example.pfa.dto.UserDTO;
import com.example.pfa.entities.Role;
import com.example.pfa.entities.Token;
import com.example.pfa.entities.User;
import com.example.pfa.repositories.RoleRepository;
import com.example.pfa.repositories.TokenRepository;
import com.example.pfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    public String register(User user) {
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).orElse(null) != null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        user.setRoles(Set.of(userRole));
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return jwtToken;
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse authenticate(AuthenticationRequest request) throws Exception {
            System.out.println(request);
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByUsernameOrEmail(request.getEmail(), request.getEmail()).orElse(null);
            // System.out.println("a00na00na00na00na00na00na00na00na00na00na00na00na00na00na00n");
            System.out.println(user);
            var jwtToken = jwtService.generateToken(user);
            // revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            List<String> roles=user.getRoles().stream().map(new Function<Role,String>() {
                @Override
                public String apply(Role role) {
                    return role.getName();
                }
            }).collect(Collectors.toList());
            return new TokenResponse(jwtToken, UserDTO.builder()
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .id(user.getId())
                    .roles(roles)
                    .build());

    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
