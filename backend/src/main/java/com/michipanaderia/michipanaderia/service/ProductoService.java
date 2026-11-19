package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.ProductoDTO;
import com.michipanaderia.michipanaderia.model.Categoria;
import com.michipanaderia.michipanaderia.model.Producto;
import com.michipanaderia.michipanaderia.repository.CategoriaRepository;
import com.michipanaderia.michipanaderia.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ProductoDTO buscarPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Producto no encontrado!"));
        return convertirADTO(producto);
    }

    public List<ProductoDTO> buscarPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaId(idCategoria).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Producto guardarProducto(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("¡La categoría indicada no existe!"));
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    public Producto actualizarProductos(Integer id, Producto producto) {
        Producto prd = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Producto no existe en los registros!"));
        if (producto.getNombre() != null) {
            prd.setNombre(producto.getNombre());
        }
        if (producto.getDescripcion() != null) {
            prd.setDescripcion(producto.getDescripcion());
        }
        if (producto.getPrecio() != null) {
            prd.setPrecio(producto.getPrecio());
        }
        if (producto.getStock() != null) {
            prd.setStock(producto.getStock());
        }
        if (producto.getImagenUrl() != null) {
            prd.setImagenUrl(producto.getImagenUrl());
        }
        if (producto.getActivo() != null) {
            prd.setActivo(producto.getActivo());
        }
        if (producto.getCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("¡La categoría indicada no existe!"));
            prd.setCategoria(categoria);
        }
        return productoRepository.save(prd);
    }

    public String eliminar(Integer id) {
        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! producto con ID " + id + " no existe."));
            productoRepository.delete(producto);
            return "producto '" + producto.getNombre() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setActivo(producto.getActivo());

        if (producto.getCategoria() != null) {
            dto.setIdCategoria(producto.getCategoria().getId());
            dto.setNombreCategoria(producto.getCategoria().getNombre());
        }

        return dto;
    }
}
