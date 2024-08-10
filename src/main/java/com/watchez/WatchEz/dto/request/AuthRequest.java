package com.watchez.WatchEz.dto.request;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class AuthRequest {
    String username;
    String email;
    String password;
}
