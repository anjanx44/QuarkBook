package org.anjanx44.userservice.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.anjanx44.userservice.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
