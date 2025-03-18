package org.anjanx44.userservice.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.anjanx44.userservice.entity.User;
import org.anjanx44.userservice.repository.UserRepository;

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

}
