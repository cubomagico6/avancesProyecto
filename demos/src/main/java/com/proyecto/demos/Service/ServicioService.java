package com.proyecto.demos.Service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.demos.Dto.DtoServiceRequest;
import com.proyecto.demos.Dto.DtoServiceResponse;
import com.proyecto.demos.Model.Servicio;
import com.proyecto.demos.Repository.ServicioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public DtoServiceResponse crear(DtoServiceRequest request) {
        Servicio servicio = mapToEntity(request);
        Servicio guardado = servicioRepository.save(servicio);
        return mapToDto(guardado);
    }

    public DtoServiceResponse obtenerPorId(Long id) {
        return servicioRepository.findByIdAndActivoTrue(id)
                .map(this::mapToDto) //este map no es el mismo que el de Stream()
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id: " + id));
    }

    public List<DtoServiceResponse> listar(){
        return servicioRepository.findByActivoTrue().stream()
        .map(s-> this.mapToDto(s))
        .collect(Collectors.toList());
    }
    
    public DtoServiceResponse actualizar(Long id, DtoServiceRequest request) {
        Servicio servicio = servicioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id: " + id));

        servicio.setNombre(request.getNombre());
        servicio.setTiempoEstimado(request.getTiempoEstimado());
        servicio.setPrecio(request.getPrecio());

        Servicio actualizado = servicioRepository.save(servicio);
        return mapToDto(actualizado);
    }

    public void eliminar(Long id) {
        Servicio servicio = servicioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id: " + id));

        servicio.setActivo(false);
        servicioRepository.save(servicio);
    }

    private Servicio mapToEntity(DtoServiceRequest dto) {
        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());
        servicio.setTiempoEstimado(dto.getTiempoEstimado());
        servicio.setPrecio(dto.getPrecio());
        return servicio;
    }

    private DtoServiceResponse mapToDto(Servicio servicio) {
        DtoServiceResponse dto = new DtoServiceResponse();
        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        dto.setPrecio(servicio.getPrecio());
        dto.setTiempoEstimado(servicio.getTiempoEstimado());
        return dto;
    }
}
