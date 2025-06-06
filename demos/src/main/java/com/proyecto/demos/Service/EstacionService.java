package com.proyecto.demos.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.demos.Dto.DtoEstacionRequest;
import com.proyecto.demos.Dto.DtoEstacionResponse;
import com.proyecto.demos.Repository.EstacionRepository;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    public DtoEstacionResponse crear(DtoEstacionRequest request){
        return null;
    }

    public DtoEstacionResponse obtenerPorId(Long id){
        return null;
    }

    public List<DtoEstacionResponse> listar(){
        return null;
    }

    public DtoEstacionResponse actualizar(Long id){
        return null;
    }   

    public void eliminar(){
        //
    }
}
