package dev.Vivek.Auth.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutRequestDto {

  private String token;

    private Long userId;

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

