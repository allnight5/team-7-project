package com.sparta.team7_project.presentation.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class DeleteRequestDto {
    private String username;
    private String password;
}