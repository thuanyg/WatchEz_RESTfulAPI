package com.watchez.WatchEz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String firstName;
    private String lastName;
    private String dob;
    private String avatarUrl;

    @OneToMany(mappedBy = "user")
    private Set<FavoriteMovie> favoriteMovies;
}
