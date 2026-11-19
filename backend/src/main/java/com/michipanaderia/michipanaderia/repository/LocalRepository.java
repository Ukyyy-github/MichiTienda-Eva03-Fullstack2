package com.michipanaderia.michipanaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michipanaderia.michipanaderia.model.Local;

@Repository 
public interface LocalRepository extends JpaRepository <Local, Integer>{
}
