package com.watchez.WatchEz.service;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.FavoriteMovieRequest;
import com.watchez.WatchEz.dto.response.FavoriteListResponse;
import com.watchez.WatchEz.dto.response.FavoriteMovieResponse;
import com.watchez.WatchEz.entity.FavoriteMovie;
import com.watchez.WatchEz.entity.User;
import com.watchez.WatchEz.exception.AppException;
import com.watchez.WatchEz.exception.ErrorCode;
import com.watchez.WatchEz.repository.FavoriteMovieRepository;
import com.watchez.WatchEz.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FavouriteMovieService {
    FavoriteMovieRepository favoriteMovieRepo;
    UserRepository userRepo;

    public ApiResponse<List<FavoriteListResponse>> getFavoriteMoviesByUserID(String userID) {

        userRepo.findById(userID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<FavoriteMovie> favoriteMovies = favoriteMovieRepo.findByUser_Id(userID);

        List<FavoriteListResponse> movieResponseList = new ArrayList<>();
        favoriteMovies.forEach(f -> {
            movieResponseList.add(FavoriteListResponse.builder().
                    favoriteMovieId(f.getFavoriteMovie_id())
                    .user_id(f.getUser().getId())
                    .slug(f.getSlug())
                    .name(f.getName())
                    .quality(f.getQuality())
                    .language(f.getLanguage())
                    .genres(f.getGenres())
                    .save_date(f.getSave_date()).
                    build()
            );
        });

        return ApiResponse.<List<FavoriteListResponse>>builder()
                .message("Get list of favorite movies successfully")
                .data(movieResponseList)
                .build();
    }

    public ApiResponse<FavoriteMovieResponse> createFavouriteMovie(FavoriteMovieRequest dataRequest) {

        User user = userRepo.findById(dataRequest.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (favoriteMovieRepo.existsByUser_IdAndSlug(dataRequest.getUser_id(), dataRequest.getSlug()))
            throw new AppException(ErrorCode.MOVIE_SAVE_EXISTED);

        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setSlug(dataRequest.getSlug());
        favoriteMovie.setUser(user);
        favoriteMovie.setName(dataRequest.getName());
        favoriteMovie.setPosterUrl(dataRequest.getPosterUrl());
        favoriteMovie.setQuality(dataRequest.getQuality());
        favoriteMovie.setLanguage(dataRequest.getLanguage());
        favoriteMovie.setGenres(dataRequest.getGenres());
        favoriteMovie.setSave_date(dataRequest.getSave_date());

        favoriteMovieRepo.save(favoriteMovie);

        FavoriteMovieResponse favoriteMovieResponse = new FavoriteMovieResponse();
        favoriteMovieResponse.setSlug(favoriteMovie.getSlug());
        favoriteMovieResponse.setUser_id(user.getId());

        return ApiResponse.<FavoriteMovieResponse>builder()
                .message("Create favorite movies successfully")
                .data(favoriteMovieResponse)
                .build();
    }


    public ApiResponse<String> deleteFavouriteMovie(Long favoriteMovieId) {
        favoriteMovieRepo.findById(favoriteMovieId).orElseThrow(() -> new AppException(ErrorCode.MOVIE_SAVE_NOT_EXISTED));
        favoriteMovieRepo.deleteById(favoriteMovieId);
        return ApiResponse.<String>builder()
                .message("Delete favorite movie successfully")
                .data(null)
                .build();
    }

}
