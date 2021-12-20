package com.example.cosmic.controller;

import com.example.cosmic.DTO.UserDTO;
import com.example.cosmic.Exceptions.AccountValidationException;
import com.example.cosmic.config.JwtProvider;
import com.example.cosmic.domain.User;
import com.example.cosmic.service.MailService;
import com.example.cosmic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private MailService mailService;



    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO user, BindingResult errors) throws MessagingException {
        if(userService.findByLogin(user.getLogin())!=null){
            errors.rejectValue("login", "", "Этот логин уже используется");
        }
        if(errors.hasErrors()) {
            throw new AccountValidationException(errors);
        }
        User u = new User();
        u.setPassword(user.getPassword());
        u.setLogin(user.getLogin());
        u.setEmail(user.getEmail());
        userService.sendMail(u);
        userService.saveUser(u);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserDTO u) {
        User user = userService.findByLoginAndPassword(u.getLogin(), u.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        Map<Object, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("accountId", user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getInfo")
    public ResponseEntity getInfo() {
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }

    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestParam Map<String, String> mapParam){
        Integer accountId = Integer.parseInt(mapParam.get("accountId"));
        User user = userService.findById(accountId);
        Boolean isAdmin = "ROLE_ADMIN".equals(user.getRole().getName());
        return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }
}