package com.michipanaderia.michipanaderia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.DetalleBoleta;

@Repository 
public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Integer> {
    List<DetalleBoleta> findByBoletaId(Integer idBoleta);
}
