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

import com.michipanaderia.michipanaderia.DTO.DetalleBoletaDTO;
import com.michipanaderia.michipanaderia.model.DetalleBoleta;
import com.michipanaderia.michipanaderia.service.DetalleBoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/detalles-boleta")
@Tag(name = "Detalle de boleta", description = "CRUD de las líneas de cada boleta")
public class DetalleBoletaController {

    @Autowired
    private DetalleBoletaService detalleBoletaService;

    @Operation(summary = "Obtener todos los detalles", description = "Devuelve la lista completa de detalles de boleta")
    @GetMapping
    public ResponseEntity<List<DetalleBoletaDTO>> obtenerTodos() {
        return ResponseEntity.ok(detalleBoletaService.obtenerTodos());
    }

    @Operation(summary = "Buscar detalle por ID", description = "Devuelve un detalle según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoletaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleBoletaService.buscarPorId(id));
    }

    @Operation(summary = "Buscar detalles por boleta", description = "Devuelve todas las líneas de una boleta")
    @GetMapping("/boleta/{idBoleta}")
    public ResponseEntity<List<DetalleBoletaDTO>> buscarPorBoleta(@PathVariable Integer idBoleta) {
        return ResponseEntity.ok(detalleBoletaService.buscarPorBoleta(idBoleta));
    }

    @Operation(summary = "Agregar detalle a una boleta", description = "Toma el precio desde el producto, calcula el subtotal de la línea y descuenta el stock")
    @PostMapping
    public ResponseEntity<DetalleBoleta> guardar(@Valid @RequestBody DetalleBoleta detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleBoletaService.guardarDetalle(detalle));
    }

    @Operation(summary = "Actualizar cantidad de un detalle", description = "Recalcula el subtotal y ajusta el stock según la diferencia")
    @PutMapping("/{id}")
    public ResponseEntity<DetalleBoleta> actualizar(@PathVariable Integer id, @RequestBody DetalleBoleta detalle) {
        return ResponseEntity.ok(detalleBoletaService.actualizarDetalles(id, detalle));
    }

    @Operation(summary = "Eliminar detalle", description = "Elimina la línea y devuelve la cantidad al stock del producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleBoletaService.eliminar(id));
    }
}