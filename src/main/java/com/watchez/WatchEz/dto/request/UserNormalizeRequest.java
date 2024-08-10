package com.watchez.WatchEz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNormalizeRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String avatarUrl;
}
