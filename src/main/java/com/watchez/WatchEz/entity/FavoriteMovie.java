package com.watchez.WatchEz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "favorites")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteMovie_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "slug")
    private String slug;

    String name;
    String quality;
    String language;
    String genres;
    Date save_date;
}
