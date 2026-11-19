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

import com.michipanaderia.michipanaderia.DTO.BoletaDTO;
import com.michipanaderia.michipanaderia.model.Boleta;
import com.michipanaderia.michipanaderia.service.BoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/boletas")
@Tag(name = "Boleta", description = "CRUD de boletas de venta")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @Operation(summary = "Obtener todas las boletas", description = "Devuelve la lista completa de boletas")
    @GetMapping
    public ResponseEntity<List<BoletaDTO>> obtenerTodos() {
        return ResponseEntity.ok(boletaService.obtenerTodos());
    }

    @Operation(summary = "Buscar boleta por ID", description = "Devuelve una boleta según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<BoletaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(boletaService.buscarPorId(id));
    }

    @Operation(summary = "Buscar boletas por usuario", description = "Devuelve las boletas emitidas a un usuario")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<BoletaDTO>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(boletaService.buscarPorUsuario(idUsuario));
    }

    @Operation(summary = "Crear boleta", description = "Registra una boleta nueva, valida que el número no se repita y que el usuario y el local existan")
    @PostMapping
    public ResponseEntity<Boleta> guardar(@Valid @RequestBody Boleta boleta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaService.guardarBoleta(boleta));
    }

    @Operation(summary = "Actualizar boleta", description = "Modifica solo los campos que se envían")
    @PutMapping("/{id}")
    public ResponseEntity<Boleta> actualizar(@PathVariable Integer id, @RequestBody Boleta boleta) {
        return ResponseEntity.ok(boletaService.actualizarBoletas(id, boleta));
    }

    @Operation(summary = "Eliminar boleta", description = "Elimina una boleta según su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(boletaService.eliminar(id));
    }
}