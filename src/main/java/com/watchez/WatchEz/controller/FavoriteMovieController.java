package com.watchez.WatchEz.controller;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.FavoriteMovieRequest;
import com.watchez.WatchEz.dto.response.FavoriteListResponse;
import com.watchez.WatchEz.dto.response.FavoriteMovieResponse;
import com.watchez.WatchEz.repository.FavoriteMovieRepository;
import com.watchez.WatchEz.service.FavouriteMovieService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FavoriteMovieController {
    FavouriteMovieService favouriteMovieService;

    @GetMapping("/{userid}")
    ApiResponse<List<FavoriteListResponse>> getFavoriteMoviesByUserID(@PathVariable("userid") String userid) {
        return favouriteMovieService.getFavoriteMoviesByUserID(userid);
    }

    @PostMapping
    ApiResponse<FavoriteMovieResponse> createFavoriteMovie(@RequestBody FavoriteMovieRequest favoriteMovieRequest) {
        return favouriteMovieService.createFavouriteMovie(favoriteMovieRequest);
    }

    @DeleteMapping("/{movieID}")
    ApiResponse<String> deleteFavoriteMovie(@PathVariable("movieID") Long favoriteMovieId) {
        return favouriteMovieService.deleteFavouriteMovie(favoriteMovieId);
    }

}
