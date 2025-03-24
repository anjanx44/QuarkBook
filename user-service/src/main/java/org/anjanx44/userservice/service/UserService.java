package org.anjanx44.userservice.service;

import io.smallrye.jwt.build.Jwt; // Use Jwt instead of BuildJwt
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.anjanx44.userservice.entity.User;
import org.anjanx44.userservice.repository.UserRepository;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.Collections; // For single role as a Set
import java.util.HashSet;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        user.persist();
        return user;
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(User.findById(id));
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = User.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return user;
    }

    @Transactional
    public Response deleteUser(Long id) {
        User user = User.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.delete();
        return Response.ok().build();
    }

    public String login(String username, String password) {
        User user = userRepository.find("username", username).firstResult();
        if (user == null || !user.getPassword().equals(password)) {
            throw new NotFoundException("Invalid credentials");
        }

//        return Jwt
////                .issuer("https://example.com/issuer")    // Issuer
//                .subject(user.getUsername())            // Subject (user identifier)
//                .groups(Collections.singleton("user"))  // Single role as a Set
//                .expiresIn(3600)                        // Expires in 1 hour
//                .sign();

        String token = Jwt.issuer("https://example.com/issuer")
                .upn("jdoe@quarkus.io")
                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                .claim(Claims.birthdate.name(), "2001-07-13")
                .sign();// Sign with configured key

        return token;
    }
}