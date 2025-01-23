package com.makeprojects.library.elib.dto;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    private String username;
    private String password;
}
