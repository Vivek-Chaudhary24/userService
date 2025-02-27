package dev.Vivek.Auth.Service;

import dev.Vivek.Auth.Dtos.UserDto;
import dev.Vivek.Auth.Models.Role;
import dev.Vivek.Auth.Models.User;
import dev.Vivek.Auth.Repository.RoleRepository;
import dev.Vivek.Auth.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private KafkaProducer kafkaProducer;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,KafkaProducer kafkaProducer) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public ResponseEntity<UserDto> getUser(Long userId){
     Optional<User> user = userRepository.findById(userId);
     if(user.isEmpty()){
         return null;
     }

     ResponseEntity<UserDto> response = new ResponseEntity<>(UserDto.from(user.get()), HttpStatus.OK);
     return response;
    }

    public UserDto setUser(Long userId, List<Long> role){
        Optional<User> userOptional = userRepository.findById(userId);
       List<Role> roles = roleRepository.findAllByIdIn(role);
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        user.setRole(new HashSet<>(roles));
        User savedUser = userRepository.save(user);
        kafkaProducer.sendMessage("user-registration","New user registered:"+savedUser.getEmail());
        return UserDto.from(savedUser);
    }
}
