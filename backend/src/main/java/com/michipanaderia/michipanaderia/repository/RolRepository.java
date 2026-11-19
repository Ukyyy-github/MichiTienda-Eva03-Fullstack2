package com.michipanaderia.michipanaderia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    boolean existsByNombre(String nombre);

    Optional<Rol> findByNombre(String nombre);
}
