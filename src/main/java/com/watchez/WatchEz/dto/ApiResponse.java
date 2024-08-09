package com.watchez.WatchEz.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    int statusCode;
    String message;
    T data;
}
