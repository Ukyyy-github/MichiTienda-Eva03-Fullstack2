package com.michipanaderia.michipanaderia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.Boleta;

@Repository 
public interface BoletaRepository extends JpaRepository<Boleta, Integer>{
    boolean existsByNumeroBoleta(String numeroBoleta);
    List<Boleta> findByUsuarioId(Integer idUsuario);
}
