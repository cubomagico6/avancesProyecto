package com.proyecto.demos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demos.Model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio,Long>{

}
