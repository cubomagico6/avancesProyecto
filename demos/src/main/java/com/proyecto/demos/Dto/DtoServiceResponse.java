package com.proyecto.demos.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoServiceResponse {
    private Long id;
    private String nombre;
    private int tiempoEstimado;
    private double precio;

}
