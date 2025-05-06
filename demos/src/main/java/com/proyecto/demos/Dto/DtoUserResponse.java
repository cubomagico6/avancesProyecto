package com.proyecto.demos.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserResponse {
    private Long id;
    private String nombre;
    private String username;
    private String correo;
    private String rol;
}
