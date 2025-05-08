package com.proyecto.demos.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.demos.Dto.DtoUserRequest;
import com.proyecto.demos.Dto.DtoUserResponse;
import com.proyecto.demos.Model.Usuario;
import com.proyecto.demos.Model.enums.Rol;
import com.proyecto.demos.Repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DtoUserResponse crear(DtoUserRequest request){
        Usuario usuario = mapDtoToUsuario(request);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapUsuarioToDto(usuarioGuardado);
    }

    public List<DtoUserResponse> listar(){
        return usuarioRepository.findByActivoTrue().stream()
        .map(u-> this.mapUsuarioToDto(u))
        .collect(Collectors.toList());
    }

    public Optional<DtoUserResponse> obtenerPorId(Long id){
        return usuarioRepository.findByIdAndActivoTrue(id).map(u->this.mapUsuarioToDto(u));
    }

    public Optional<DtoUserResponse> actualizar(Long id, DtoUserRequest request){
        return usuarioRepository.findByIdAndActivoTrue(id)
            .map(usuarioExistente-> {
                usuarioExistente.setNombre(request.getNombre());
                usuarioExistente.setCorreo(request.getCorreo());
                usuarioExistente.setUsername(request.getUsername());
                if (request.getPassword() !=null && !request.getPassword().isEmpty()) {
                    usuarioExistente.setContrasena(passwordEncoder.encode(request.getPassword()));
                }
                usuarioExistente.setRol(Rol.valueOf(request.getRol().toUpperCase()));
                Usuario usuarioActualizado=usuarioRepository.save(usuarioExistente);
                return mapUsuarioToDto(usuarioActualizado);
            });
    }

    public void eliminar(Long id){
        Optional<Usuario> usuario=usuarioRepository.findById(id);
        if (usuario.isPresent()) {
        Usuario u=usuario.get();
        u.setActivo(false);
        usuarioRepository.save(u);
        }
    }

    private Usuario mapDtoToUsuario(DtoUserRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setUsername(request.getUsername());
        usuario.setContrasena(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.valueOf(request.getRol().toUpperCase()));
        return usuario;
    }
    private DtoUserResponse mapUsuarioToDto(Usuario usuario) {
        return new DtoUserResponse(usuario.getId(),
                usuario.getNombre(), usuario.getUsername(), 
                usuario.getCorreo(), usuario.getRol().name());
    }
}
