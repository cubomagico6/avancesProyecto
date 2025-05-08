package com.proyecto.demos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demos.Model.Estacion;
@Repository
public interface EstacionRepository extends JpaRepository<Estacion,Long>{

}
