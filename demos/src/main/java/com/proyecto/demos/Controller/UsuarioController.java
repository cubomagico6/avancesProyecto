package com.proyecto.demos.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demos.Dto.DtoUserRequest;
import com.proyecto.demos.Dto.DtoUserResponse;
import com.proyecto.demos.Service.UsuarioService;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Metodos, tener en cuenta el GlobalExceptionHandler

    @PostMapping
    public ResponseEntity<DtoUserResponse> crear(@RequestBody DtoUserRequest request){
        DtoUserResponse response=usuarioService.crear(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUserResponse> buscarPorId(@PathVariable Long id){
        Optional<DtoUserResponse> u=usuarioService.obtenerPorId(id);
        DtoUserResponse response=u.get();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DtoUserResponse>> listar(){
        List<DtoUserResponse> response=usuarioService.listar();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoUserResponse> actualizar(@PathVariable Long id, @RequestBody DtoUserRequest request){
        Optional<DtoUserResponse> u=usuarioService.actualizar(id, request);
        DtoUserResponse response=u.get();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
