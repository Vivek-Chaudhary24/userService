package dev.Vivek.Auth.Controller;

import dev.Vivek.Auth.Dtos.CreateRoleRequestDto;
import dev.Vivek.Auth.Models.Role;
import dev.Vivek.Auth.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto){
        Role role = roleService.createRole(createRoleRequestDto.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
