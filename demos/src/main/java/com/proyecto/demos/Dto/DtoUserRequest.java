package com.proyecto.demos.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserRequest {
    private String nombre;
    private String username;
    private String correo;
    private String password;
    private String rol; //
}
