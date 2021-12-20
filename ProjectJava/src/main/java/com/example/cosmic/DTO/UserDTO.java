package com.example.cosmic.DTO;

import com.example.cosmic.domain.User;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserDTO {

    @NotBlank(message = "Поле логин не может быть пустым")
    @Length(min = 8, max = 40, message = "Длина логина должна быть от 8 до 40 символов")
    private String login;

    @NotBlank(message = "Поле пароль не может быть пустым")
    @Length(min = 8, max = 16, message = "Длина пароля должна быть от 8 до 16 символов")
    private String password;

    @Email
    private String email;
}