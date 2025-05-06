package com.proyecto.demos.Model.enums;


import java.util.Arrays;
import java.util.List;

public enum Rol {
    ADMINISTRADOR(Arrays.asList(Permisos.READ,Permisos.CREATE,Permisos.UPDATE,Permisos.DELETE)),
    BARBERO(Arrays.asList(Permisos.READ));

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
