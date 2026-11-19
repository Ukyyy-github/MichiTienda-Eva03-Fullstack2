package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.LocalDTO;
import com.michipanaderia.michipanaderia.model.Local;
import com.michipanaderia.michipanaderia.repository.LocalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LocalService {
    
    @Autowired
    private LocalRepository localRepository;

    public List<LocalDTO> obtenerTodos() {
        return localRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public LocalDTO buscarPorId(Integer id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Local no encontrado!"));
        return convertirADTO(local);
    }

    public Local guardarLocal(Local local) {
        return localRepository.save(local);
    }

    public Local actualizarLocales(Integer id, Local local) {
        Local lcl = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Local no existe en los registros!"));
        if (local.getNombre() != null) {
            lcl.setNombre(local.getNombre());
        }
        if (local.getDireccion() != null) {
            lcl.setDireccion(local.getDireccion());
        }
        if (local.getComunaCiudad() != null) {
            lcl.setComunaCiudad(local.getComunaCiudad());
        }
        if (local.getTelefono() != null) {
            lcl.setTelefono(local.getTelefono());
        }
        if (local.getHorarioAtencion() != null) {
            lcl.setHorarioAtencion(local.getHorarioAtencion());
        }
        return localRepository.save(lcl);
    }

    public String eliminar(Integer id) {
        try {
            Local local = localRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! local con ID " + id + " no existe."));
            localRepository.delete(local);
            return "local '" + local.getNombre() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private LocalDTO convertirADTO(Local local) {
        LocalDTO dto = new LocalDTO();
        dto.setId(local.getId());
        dto.setNombre(local.getNombre());
        dto.setDireccion(local.getDireccion());
        dto.setComunaCiudad(local.getComunaCiudad());
        dto.setTelefono(local.getTelefono());
        dto.setHorarioAtencion(local.getHorarioAtencion());
        return dto;
    }
}
