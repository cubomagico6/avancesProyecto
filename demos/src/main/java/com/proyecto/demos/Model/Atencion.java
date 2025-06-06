package com.proyecto.demos.Model;

import java.time.LocalDateTime;

import com.proyecto.demos.Model.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCliente; //opcional o colocar "frecuente"

    @Column(nullable = false)
    private LocalDateTime horaInicio;

    @Column(nullable = false)
    private LocalDateTime horaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    private Empleado barbero; //usuario con rol de barbero

    @ManyToOne(optional = false)
    @JoinColumn(name = "estacion_id")
    private Estacion estacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "creado_por_admin_id", nullable = false) 
    private Usuario creadoPor;

    @Column(nullable = false, updatable = false) // No se actualiza una vez creado
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "ultima_modificacion_por_admin_id") // Puede ser nulo si nunca se modificó
    private Usuario ultimaModificacionPor;

    @Column // Puede ser nulo si nunca se modificó
    private LocalDateTime fechaUltimaModificacion;
}
