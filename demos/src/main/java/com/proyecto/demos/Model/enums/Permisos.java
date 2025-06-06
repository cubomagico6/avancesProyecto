package com.proyecto.demos.Model.enums;

public enum Permisos {
    // Permisos generales de la aplicación
    DASHBOARD_VIEW,

    // Permisos para la gestión de Atenciones (Registros de Servicio)
    ATENCION_READ,
    ATENCION_CREATE,
    ATENCION_UPDATE,
    ATENCION_DELETE,

    // Permisos para la gestión de Empleados (Barberos)
    EMPLEADO_READ,
    EMPLEADO_CREATE,
    EMPLEADO_UPDATE,
    EMPLEADO_DELETE,

    // Permisos para la gestión de Servicios
    SERVICIO_READ,
    SERVICIO_CREATE,
    SERVICIO_UPDATE,
    SERVICIO_DELETE,

    // Permisos para la gestión de Estaciones
    ESTACION_READ,
    ESTACION_CREATE,
    ESTACION_UPDATE,
    ESTACION_DELETE,

    // Permisos específicos del Super Administrador y ahora del Recepcionista para lectura
    ADMINISTRADOR_MANAGE,   // Permiso exclusivo para SUPER_ADMIN (CRUD completo de otros administradores/asistentes)
    ADMINISTRADOR_READ,     // **Nuevo permiso:** Para leer la lista de usuarios (administradores=usuarios)

    REPORTES_ACCESO,
    CONFIGURACION_SISTEMA
}
