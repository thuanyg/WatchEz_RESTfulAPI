package com.watchez.WatchEz.dto.response;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class VerifyTokenResponse {
    boolean valid;
    String userid;
}
