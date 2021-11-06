package com.example.cosmic.DTO;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class SongDTO {

    private Integer userId;

    private Integer songId;


}