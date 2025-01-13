package dev.Vivek.Auth.Dtos;

public class ValidateDto {
    String token;
    Long userId;

    public void setToken(String token) {
        this.token = token;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getToken() {
        return token;
    }
    public Long getUserId() {
        return userId;
    }
}
