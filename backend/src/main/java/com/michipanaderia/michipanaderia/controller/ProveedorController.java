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

import com.michipanaderia.michipanaderia.DTO.ProveedorDTO;
import com.michipanaderia.michipanaderia.model.Proveedor;
import com.michipanaderia.michipanaderia.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedor", description = "CRUD de proveedores de insumos")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Operation(summary = "Obtener todos los proveedores", description = "Devuelve la lista completa de proveedores")
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> obtenerTodos() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    @Operation(summary = "Buscar proveedor por ID", description = "Devuelve un proveedor según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.buscarPorId(id));
    }

    @Operation(summary = "Crear proveedor", description = "Registra un proveedor nuevo con sus datos de contacto")
    @PostMapping
    public ResponseEntity<Proveedor> guardar(@Valid @RequestBody Proveedor proveedor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.guardarProveedor(proveedor));
    }

    @Operation(summary = "Actualizar proveedor", description = "Modifica los datos de un proveedor existente")
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer id, @RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorService.actualizarProveedores(id, proveedor));
    }

    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor según su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.eliminar(id));
    }
}