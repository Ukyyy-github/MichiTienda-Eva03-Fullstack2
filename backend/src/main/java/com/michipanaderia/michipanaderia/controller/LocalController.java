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

import com.michipanaderia.michipanaderia.DTO.LocalDTO;
import com.michipanaderia.michipanaderia.model.Local;
import com.michipanaderia.michipanaderia.service.LocalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/locales")
@Tag(name = "Local", description = "CRUD de locales de la panadería")
public class LocalController {

    @Autowired
    private LocalService localService;

    @Operation(summary = "Obtener todos los locales", description = "Devuelve la lista completa de locales")
    @GetMapping
    public ResponseEntity<List<LocalDTO>> obtenerTodos() {
        return ResponseEntity.ok(localService.obtenerTodos());
    }

    @Operation(summary = "Buscar local por ID", description = "Devuelve un local según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<LocalDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(localService.buscarPorId(id));
    }

    @Operation(summary = "Crear local", description = "Registra un local nuevo con su dirección y horario")
    @PostMapping
    public ResponseEntity<Local> guardar(@Valid @RequestBody Local local) {
        return ResponseEntity.status(HttpStatus.CREATED).body(localService.guardarLocal(local));
    }

    @Operation(summary = "Actualizar local", description = "Modifica los datos de un local existente")
    @PutMapping("/{id}")
    public ResponseEntity<Local> actualizar(@PathVariable Integer id, @RequestBody Local local) {
        return ResponseEntity.ok(localService.actualizarLocales(id, local));
    }

    @Operation(summary = "Eliminar local", description = "Elimina un local según su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(localService.eliminar(id));
    }
}