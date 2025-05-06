package com.proyecto.demos.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.proyecto.demos.Model.Usuario;
import com.proyecto.demos.Repository.UsuarioRepository;

@Component
public class InfoUserDetailsService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userinfo=usuarioRepository.findByUsername(username);
        return userinfo.map(InfoUserDetails::new) //para retornar un UserDetails
            .orElseThrow(() -> new UsernameNotFoundException("user not found " +username));
    }
}
