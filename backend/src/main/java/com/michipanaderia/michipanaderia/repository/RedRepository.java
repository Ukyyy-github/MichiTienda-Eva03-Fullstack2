package com.michipanaderia.michipanaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.Red;

@Repository
public interface RedRepository extends JpaRepository<Red, Integer> {

}
