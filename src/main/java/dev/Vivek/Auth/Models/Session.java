package dev.Vivek.Auth.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseClass {
    private String token;
    private Date expiringAt;

    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;

    @ManyToOne
    private User user;

    public void setExpiringAt(Date expiringAt) {
        this.expiringAt = expiringAt;
    }
    public Date getExpiringAt() {
        return expiringAt;
    }

    public void setSessionStatus(SessionStatus status) {
        this.status = status;
      }
      public SessionStatus getSessionStatus() {
        return status;
      }

      public void setToken(String token) {
        this.token = token;
      }
      public String getToken() {
        return token;
      }

      public void setUser(User user) {
        this.user = user;
      }

      public User getUser() {
        return user;
      }

}
