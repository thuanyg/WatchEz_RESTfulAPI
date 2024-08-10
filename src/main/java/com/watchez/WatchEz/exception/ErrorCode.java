package com.watchez.WatchEz.exception;

public enum ErrorCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // Custom status codes
    USER_EXISTED(1001, "User already existed"),
    USER_NOT_EXISTED(1002, "User does not exist"),
    NO_USERS_FOUND(1003, "No users found"),
    USER_CREATION_FAILED(1004, "User creation failed"),
    USER_UPDATE_FAILED(1005, "User update failed"),
    INVALID_LOGIN(1006, "Email or username not exist."),
    MOVIE_SAVE_EXISTED(1007, "Favorite movie already existed by this user."),
    MOVIE_SAVE_NOT_EXISTED(1008, "Favorite movie not exist");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
