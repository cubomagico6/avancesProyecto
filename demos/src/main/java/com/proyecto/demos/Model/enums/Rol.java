package com.proyecto.demos.Model.enums;


import java.util.Arrays;
import java.util.List;

public enum Rol {
    // Rol para el Dueño o la máxima autoridad
    // Tiene acceso total y permisos exclusivos para gestión de administradores, reportes clave y configuración.
    ADMINISTRADOR(Arrays.asList( // Renombramos de SUPER_ADMIN a ADMINISTRADOR para simplificar
        Permisos.DASHBOARD_VIEW,
        Permisos.ATENCION_READ, Permisos.ATENCION_CREATE, Permisos.ATENCION_UPDATE, Permisos.ATENCION_DELETE,
        Permisos.EMPLEADO_READ, Permisos.EMPLEADO_CREATE, Permisos.EMPLEADO_UPDATE, Permisos.EMPLEADO_DELETE,
        Permisos.SERVICIO_READ, Permisos.SERVICIO_CREATE, Permisos.SERVICIO_UPDATE, Permisos.SERVICIO_DELETE,
        Permisos.ESTACION_READ, Permisos.ESTACION_CREATE, Permisos.ESTACION_UPDATE, Permisos.ESTACION_DELETE,
        Permisos.ADMINISTRADOR_MANAGE,   // Permiso exclusivo para gestionar otras cuentas de ADMINISTRADOR
        Permisos.REPORTES_ACCESO,       // Permiso exclusivo para acceder a reportes sensibles
        Permisos.CONFIGURACION_SISTEMA  // Permiso exclusivo para configuraciones críticas
    )),

    // Rol para el personal operativo (Recepcionista/Asistente)
    // Su rol principal es el registro y seguimiento de atenciones, y la consulta de información general.
    RECEPCIONISTA(Arrays.asList(
        Permisos.DASHBOARD_VIEW,
        Permisos.ATENCION_READ, Permisos.ATENCION_CREATE, Permisos.ATENCION_UPDATE, Permisos.ATENCION_DELETE, // CRUD completo en Atenciones
        Permisos.EMPLEADO_READ,         // Solo puede ver empleados
        Permisos.SERVICIO_READ, Permisos.SERVICIO_CREATE, Permisos.SERVICIO_UPDATE, Permisos.SERVICIO_DELETE, // CRUD completo en Servicios
        Permisos.ESTACION_READ, Permisos.ESTACION_CREATE, Permisos.ESTACION_UPDATE, Permisos.ESTACION_DELETE, // CRUD completo en Estaciones
        // Permiso específico para leer otros administradores (usuarios del sistema)
        Permisos.ADMINISTRADOR_READ     // Nuevo permiso para leer usuarios
    ));

    private List<Permisos> permisos;

    private Rol(List<Permisos> permisos){
        this.permisos=permisos;
    }
    
    public List<Permisos> getPermisos() {
        return permisos;
    }
    public void setPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }
}
