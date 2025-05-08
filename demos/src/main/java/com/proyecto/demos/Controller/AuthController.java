package com.proyecto.demos.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demos.Dto.DtoAuth;
import com.proyecto.demos.Dto.DtoJwt;
import com.proyecto.demos.Dto.DtoUserRequest;
import com.proyecto.demos.Dto.DtoUserResponse;
import com.proyecto.demos.Model.Usuario;
import com.proyecto.demos.Service.Jwt.AuthenticationService;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DtoAuth dtoUsuario){
            DtoJwt dtoJwt = authenticationService.login(dtoUsuario);
            return ResponseEntity.ok(dtoJwt);
    }

    @PostMapping("/register")
    public ResponseEntity<DtoUserResponse> register(@RequestBody DtoUserRequest request){
        Usuario usuario=authenticationService.register(request);
        DtoUserResponse response=new DtoUserResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setUsername(usuario.getUsername());
        response.setCorreo(usuario.getCorreo());
        response.setRol(usuario.getRol().name()); 
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    //agregar los servicios de crudUsuario
}
