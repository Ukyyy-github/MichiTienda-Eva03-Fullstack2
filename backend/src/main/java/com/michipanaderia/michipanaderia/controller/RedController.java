package com.michipanaderia.michipanaderia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michipanaderia.michipanaderia.DTO.RedDTO;
import com.michipanaderia.michipanaderia.model.Red;
import com.michipanaderia.michipanaderia.service.RedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/redes")
@Tag(name = "Red social", description = "CRUD de redes sociales de la panadería")
public class RedController {

    @Autowired
    private RedService redService;

    @Operation(summary = "Obtener todas las redes", description = "Devuelve la lista completa de redes sociales")
    @GetMapping
    public ResponseEntity<List<RedDTO>> obtenerTodos() {
        return ResponseEntity.ok(redService.obtenerTodos());
    }

    @Operation(summary = "Buscar red por ID", description = "Devuelve una red social según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<RedDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(redService.buscarPorId(id));
    }

    @Operation(summary = "Crear red", description = "Registra una red social nueva")
    @PostMapping
    public ResponseEntity<Red> guardar(@Valid @RequestBody Red red) {
        return ResponseEntity.status(HttpStatus.CREATED).body(redService.guardarRed(red));
    }

    @Operation(summary = "Actualizar red", description = "Modifica los datos de una red social existente")
    @PutMapping("/{id}")
    public ResponseEntity<Red> actualizar(@PathVariable Integer id, @RequestBody Red red) {
        return ResponseEntity.ok(redService.actualizarRedes(id, red));
    }

    @Operation(summary = "Eliminar red", description = "Elimina una red social según su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(redService.eliminar(id));
    }
}