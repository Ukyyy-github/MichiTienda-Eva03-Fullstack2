package com.michipanaderia.michipanaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.Proveedor;

@Repository 
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

}
