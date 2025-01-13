package dev.Vivek.Auth.Controller;

import dev.Vivek.Auth.Dtos.LogOutRequestDto;
import dev.Vivek.Auth.Dtos.LoginRequestDto;
import dev.Vivek.Auth.Dtos.UserDto;
import dev.Vivek.Auth.Dtos.ValidateDto;
import dev.Vivek.Auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService= authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
       return authService.login(loginRequestDto.getEmail() , loginRequestDto.getPassword());
    }

    @PostMapping("/signup")
    public String SignUp(@RequestBody LoginRequestDto loginRequestDto){
        return authService.SignUp(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/logout")
    public String logout(@RequestBody LogOutRequestDto logoutRequestDto){
        return authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());

    }

    @PostMapping("/validate")
    public String validateToken(@RequestBody ValidateDto validateDto){
       return authService.validate(validateDto.getToken(), validateDto.getUserId());
    }
}
