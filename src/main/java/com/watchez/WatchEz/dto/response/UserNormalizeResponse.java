package com.watchez.WatchEz.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNormalizeResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String avatarUrl;
}
