package org.anjanx44.userservice.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends PanacheEntity {
    private String username;
    private String email;
    private String password;
}
