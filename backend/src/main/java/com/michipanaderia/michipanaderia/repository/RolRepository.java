package com.michipanaderia.michipanaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    boolean existsByNombre(String nombre);
}
