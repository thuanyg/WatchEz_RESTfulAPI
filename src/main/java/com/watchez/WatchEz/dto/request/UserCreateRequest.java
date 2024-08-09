package com.watchez.WatchEz.dto.request;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    String username;
    String password;
    String firstName;
    String lastName;
    String gender;
    String email;
    String dob;
    String avatarUrl;
}
