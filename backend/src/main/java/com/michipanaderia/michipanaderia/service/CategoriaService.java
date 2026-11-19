package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.CategoriaDTO;
import com.michipanaderia.michipanaderia.model.Categoria;
import com.michipanaderia.michipanaderia.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> obtenerTodos() {
        return categoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public CategoriaDTO buscarPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Categoría no encontrada!"));
        return convertirADTO(categoria);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("¡Ya existe una categoría con ese nombre!");
        }
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategorias(Integer id, Categoria categoria) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Categoría no existe en los registros!"));
        if (categoria.getNombre() != null) {
            if (!cat.getNombre().equals(categoria.getNombre()) && categoriaRepository.existsByNombre(categoria.getNombre())) {
                throw new RuntimeException("¡Ya existe una categoría con ese nombre!");
            }
            cat.setNombre(categoria.getNombre());
        }
        if (categoria.getDescripcion() != null) {
            cat.setDescripcion(categoria.getDescripcion());
        }
        return categoriaRepository.save(cat);
    }

    public String eliminar(Integer id) {
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! categoría con ID " + id + " no existe."));
            categoriaRepository.delete(categoria);
            return "categoría '" + categoria.getNombre() + "' ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
