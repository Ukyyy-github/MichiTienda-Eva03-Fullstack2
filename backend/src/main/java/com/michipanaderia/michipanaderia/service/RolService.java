package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.RolDTO;
import com.michipanaderia.michipanaderia.model.Rol;
import com.michipanaderia.michipanaderia.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<RolDTO> obtenerTodos() {
        return rolRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public RolDTO buscarPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Rol no encontrado!"));
        return convertirADTO(rol);
    }

    public Rol guardarRol(Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new RuntimeException("¡Ya existe un rol con ese nombre!");
        }
        return rolRepository.save(rol);
    }

    public Rol actualizarRoles(Integer id, Rol rol) {
        Rol rl = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Rol no existe en los registros!"));
        if (rol.getNombre() != null) {
            if (!rl.getNombre().equals(rol.getNombre()) && rolRepository.existsByNombre(rol.getNombre())) {
                throw new RuntimeException("¡Ya existe un rol con ese nombre!");
            }
            rl.setNombre(rol.getNombre());
        }
        return rolRepository.save(rl);
    }

    public String eliminar(Integer id) {
        try {
            Rol rol = rolRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! rol con ID " + id + " no existe."));
            rolRepository.delete(rol);
            return "rol '" + rol.getNombre() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private RolDTO convertirADTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        return dto;
    }
}
