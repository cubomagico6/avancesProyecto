package com.proyecto.demos.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoServiceRequest {
    private String nombre;
    private int tiempoEstimado;
    private double precio;
}
