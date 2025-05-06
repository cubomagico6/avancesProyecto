package com.proyecto.demos.Security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.proyecto.demos.Model.Usuario;

public class InfoUserDetails implements UserDetails{

    private String username;
    private String password;
    private List<GrantedAuthority> autoridades;

    public InfoUserDetails(Usuario usuario){
        username=usuario.getUsername();
        password=usuario.getContrasena();
        autoridades=usuario.getRol().getPermisos().stream()
        .map(permisos->new SimpleGrantedAuthority(permisos.name()))
        .collect(Collectors.toList());
        autoridades.add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().name())));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autoridades;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
