package com.watchez.WatchEz.dto.response;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateResponse {
    String email;
    String username;
}
