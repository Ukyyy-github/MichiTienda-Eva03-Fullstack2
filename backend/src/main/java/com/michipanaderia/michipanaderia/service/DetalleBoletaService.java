package com.michipanaderia.michipanaderia.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.DetalleBoletaDTO;
import com.michipanaderia.michipanaderia.model.Boleta;
import com.michipanaderia.michipanaderia.model.DetalleBoleta;
import com.michipanaderia.michipanaderia.model.Producto;
import com.michipanaderia.michipanaderia.repository.BoletaRepository;
import com.michipanaderia.michipanaderia.repository.DetalleBoletaRepository;
import com.michipanaderia.michipanaderia.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleBoletaService {

    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<DetalleBoletaDTO> obtenerTodos() {
        return detalleBoletaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public DetalleBoletaDTO buscarPorId(Integer id) {
        DetalleBoleta detalle = detalleBoletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Detalle no encontrado!"));
        return convertirADTO(detalle);
    }

    public List<DetalleBoletaDTO> buscarPorBoleta(Integer idBoleta) {
        return detalleBoletaRepository.findByBoletaId(idBoleta).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public DetalleBoleta guardarDetalle(DetalleBoleta detalle) {
        Boleta boleta = boletaRepository.findById(detalle.getBoleta().getId())
                .orElseThrow(() -> new RuntimeException("¡La boleta indicada no existe!"));
        Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("¡El producto indicado no existe!"));

        if (!producto.getActivo()) {
            throw new RuntimeException("¡El producto '" + producto.getNombre() + "' no está disponible!");
        }
        if (producto.getStock() < detalle.getCantidad()) {
            throw new RuntimeException("¡Stock insuficiente! Quedan " + producto.getStock() + " unidades.");
        }

        detalle.setBoleta(boleta);
        detalle.setProducto(producto);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotalLinea(producto.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())));

        producto.setStock(producto.getStock() - detalle.getCantidad());
        productoRepository.save(producto);

        return detalleBoletaRepository.save(detalle);
    }

    public DetalleBoleta actualizarDetalles(Integer id, DetalleBoleta detalle) {
        DetalleBoleta dtl = detalleBoletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Detalle no existe en los registros!"));
        if (detalle.getCantidad() != null) {
            Producto producto = dtl.getProducto();
            int diferencia = detalle.getCantidad() - dtl.getCantidad();
            if (diferencia > 0 && producto.getStock() < diferencia) {
                throw new RuntimeException("¡Stock insuficiente! Quedan " + producto.getStock() + " unidades.");
            }
            producto.setStock(producto.getStock() - diferencia);
            productoRepository.save(producto);

            dtl.setCantidad(detalle.getCantidad());
            dtl.setSubtotalLinea(dtl.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())));
        }
        return detalleBoletaRepository.save(dtl);
    }

    public String eliminar(Integer id) {
        try {
            DetalleBoleta detalle = detalleBoletaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! detalle con ID " + id + " no existe."));
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);

            detalleBoletaRepository.delete(detalle);
            return "detalle con ID " + id + " ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private DetalleBoletaDTO convertirADTO(DetalleBoleta detalle) {
        DetalleBoletaDTO dto = new DetalleBoletaDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotalLinea(detalle.getSubtotalLinea());

        if (detalle.getBoleta() != null) {
            dto.setIdBoleta(detalle.getBoleta().getId());
            dto.setNumeroBoleta(detalle.getBoleta().getNumeroBoleta());
        }
        if (detalle.getProducto() != null) {
            dto.setIdProducto(detalle.getProducto().getId());
            dto.setNombreProducto(detalle.getProducto().getNombre());
        }

        return dto;
    }
}
