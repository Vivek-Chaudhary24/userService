package dev.Vivek.Auth.Controller;

import dev.Vivek.Auth.Dtos.*;
import dev.Vivek.Auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/{id}")
    public GenericProductDto login(@PathVariable Long id){

       return authService.login(id);
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

    @GetMapping("/check")
    public String check(){
        RestTemplate restTemplate = new RestTemplate();
        String url= "http://localhost:5000/api/python/check";
       System.out.println("In python api");
        return restTemplate.getForObject(url,String.class);

       // return "App is working fine";
    }

}
