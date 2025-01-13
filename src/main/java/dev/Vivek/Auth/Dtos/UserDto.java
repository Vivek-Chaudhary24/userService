package dev.Vivek.Auth.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.Vivek.Auth.Models.Role;
import dev.Vivek.Auth.Models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class UserDto {
    private String email;

    @JsonIgnore
    private Set<Role> role = new HashSet<>();

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
    public Set<Role> getRole() {
        return role;
    }

    public static UserDto from (User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
