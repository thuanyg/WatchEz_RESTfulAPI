package com.watchez.WatchEz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteListResponse {
    Long favoriteMovieId;
    String user_id;
    String slug;
    String name;
    String quality;
    String language;
    String genres;
    Date save_date;
}
