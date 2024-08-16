package com.watchez.WatchEz.repository;

import com.watchez.WatchEz.entity.FavoriteMovie;
import com.watchez.WatchEz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    List<FavoriteMovie> findByUser_Id(String user_id);
    boolean existsByUser_IdAndSlug(String user_id, String slug);
}
