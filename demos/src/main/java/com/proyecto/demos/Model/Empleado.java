package com.proyecto.demos.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = true) // Opcional si quieres guardar un teléfono de contacto
    private String telefono;
    // Otros datos relevantes del empleado, como DNI, fecha de contratación, etc.
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false)
    private boolean activo = true; // Para indicar si el empleado sigue trabajando
    // Podrías añadir un campo para sueldo, porcentaje por servicio, etc., si lo necesitas
}
