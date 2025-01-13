package dev.Vivek.Auth.Controller;

import dev.Vivek.Auth.Dtos.SetUserRolesDto;
import dev.Vivek.Auth.Dtos.UserDto;
import dev.Vivek.Auth.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
            this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto>  getUser(@PathVariable ("id") Long userId) {
       System.out.println("We are here"+userId);
        return userService.getUser(userId);


    }

    @PostMapping("/{id}/role")
    public ResponseEntity<UserDto> setUser(@PathVariable ("id") Long userId ,@RequestBody SetUserRolesDto setUserRolesDto  ){

        UserDto userDto = userService.setUser(userId, setUserRolesDto.getRoleIds());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
