package dev.Vivek.Auth.Service;

import dev.Vivek.Auth.Models.Role;
import dev.Vivek.Auth.Repository.RoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName) {
       Role role = new Role();
       role.setRole(roleName);
       return roleRepository.save(role);
    }
}
