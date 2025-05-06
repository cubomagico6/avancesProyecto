package com.proyecto.demos.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoAuth {
    private String username;
    private String password;
}
