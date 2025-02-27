package dev.Vivek.Auth.Service;

import dev.Vivek.Auth.Dtos.GenericProductDto;
import dev.Vivek.Auth.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import dev.Vivek.Auth.Models.Session;
import dev.Vivek.Auth.Models.SessionStatus;
import dev.Vivek.Auth.Repository.SessionRepository;
import org.apache.commons.lang3.time.DateUtils;
import dev.Vivek.Auth.Dtos.UserDto;
import dev.Vivek.Auth.Models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    RestTemplate restTemplate;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SessionRepository sessionRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    public AuthService(KafkaProducer kafkaProducer,UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.kafkaProducer = kafkaProducer;
    }

    public GenericProductDto login (Long id) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//
//        if(userOptional.isEmpty()){
//            return null;
//        }
//        User user = userOptional.get();
//        if(!bCryptPasswordEncoder.matches(password,user.getPassword())) {
//            throw new RuntimeException(("Wrong password Entered"));
//        }

        String serviceurl = "http://PRODUCT/products/"+id;
//        MacAlgorithm alg = Jwts.SIG.HS256;
//        SecretKey key = alg.key().build();
//
//        Map<String,Object> jsonMap = new HashMap<>();
//        jsonMap.put("email",user.getEmail());
//        jsonMap.put("createdAt", new Date());
//        jsonMap.put("expiryAt",DateUtils.addDays(new Date(),30));
//
//        String jws = Jwts.builder().claims(jsonMap).signWith(key,alg).compact();
//
//        Session session = new Session();
//        session.setSessionStatus(SessionStatus.ACTIVE);
//        session.setToken(jws);
//        session.setUser(user);
//        session.setExpiringAt(new Date());
//         System.out.println(session);
//        sessionRepository.save(session);
//
//        UserDto userDto = new UserDto();
//        userDto.setEmail(user.getEmail());
//        System.out.println(userDto);
//        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
//        //headers.add(HttpHeaders.SET_COOKIE,"auth-token:"+jws);
//        headers.add("Content-Type", "application/json");
//        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto,headers, HttpStatus.OK);
       GenericProductDto genericProductDto= restTemplate.getForObject(serviceurl,GenericProductDto.class);
        return genericProductDto;

    }
    public ResponseEntity<UserDto> login (String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())) {
            throw new RuntimeException(("Wrong password Entered"));
        }

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("email",user.getEmail());
        jsonMap.put("createdAt", new Date());
        jsonMap.put("expiryAt",DateUtils.addDays(new Date(),30));

        String jws = Jwts.builder().claims(jsonMap).signWith(key,alg).compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(jws);
        session.setUser(user);
        session.setExpiringAt(new Date());
        System.out.println(session);
        sessionRepository.save(session);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        System.out.println(userDto);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        //headers.add(HttpHeaders.SET_COOKIE,"auth-token:"+jws);
        headers.add("Content-Type", "application/json");
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto,headers, HttpStatus.OK);
        return response;

    }

public String SignUp(String email, String password){
    Optional<User> userOptional = userRepository.findByEmail(email);
    if(userOptional.isPresent()){
        return"User already exists";
    }
    else{
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        kafkaProducer.sendMessage("user-registration","New user registered:"+savedUser.getEmail());
        return "User created"+"  "+ "Your email id is "+user.getEmail();
    }
}
//
//public String logout(String email, String password){
//    Optional<User> userOptional = userRepository.findByEmail(email);
//    if(userOptional.isPresent()) {
//        if (bCryptPasswordEncoder.matches(password, userOptional.get().getPassword()))
//        {
//            User user = userOptional.get();
//            user.setSessionStatus(SessionStatus.ENDED);
//            return "User logged out";
//        }
//          else{
//            return "Incorrect Password ";
//        }
//    }
//    return "Incorrect Email Id";
//}

    public String logout(String token, Long userId) {
     Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
      if(sessionOptional.isEmpty()){
             return "User not found";
           }
      Session session = sessionOptional.get();
      session.setSessionStatus(SessionStatus.ENDED);
      sessionRepository.save(session);
      return "User logged out";
    }

    public String validate( String token,Long userId){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty()){
            return "Invalid token or userId";
        }
        Session session = sessionOptional.get();

        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
                   return "Session is not active";
        }

        Date currentTime = new Date();
        if(session.getExpiringAt().before(currentTime)){
            return "Expired session";
        }
        Jws<Claims> jws= Jwts.parser().build().parseSignedClaims(token);
        String email=(String) jws.getPayload().get("email");
       // List<Role> roles = (List) jws.getPayload().get("roles");
        Date createdAt = (Date) jws.getPayload().get("createdAt");
        return "Session is Active";
    }

}