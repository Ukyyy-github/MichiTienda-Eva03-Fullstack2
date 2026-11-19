package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.RedDTO;
import com.michipanaderia.michipanaderia.model.Red;
import com.michipanaderia.michipanaderia.repository.RedRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RedService {

    @Autowired
    private RedRepository redRepository;

    public List<RedDTO> obtenerTodos() {
        return redRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public RedDTO buscarPorId(Integer id) {
        Red red = redRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Red no encontrada!"));
        return convertirADTO(red);
    }

    public Red guardarRed(Red red) {
        return redRepository.save(red);
    }

    public Red actualizarRedes(Integer id, Red red) {
        Red rd = redRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Red no existe en los registros!"));
        if (red.getPlataforma() != null) {
            rd.setPlataforma(red.getPlataforma());
        }
        if (red.getUrl() != null) {
            rd.setUrl(red.getUrl());
        }
        if (red.getUsuarioHandle() != null) {
            rd.setUsuarioHandle(red.getUsuarioHandle());
        }
        return redRepository.save(rd);
    }

    public String eliminar(Integer id) {
        try {
            Red red = redRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! red con ID " + id + " no existe."));
            redRepository.delete(red);
            return "red '" + red.getPlataforma() + "' ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private RedDTO convertirADTO(Red red) {
        RedDTO dto = new RedDTO();
        dto.setId(red.getId());
        dto.setPlataforma(red.getPlataforma());
        dto.setUrl(red.getUrl());
        dto.setUsuarioHandle(red.getUsuarioHandle());
        return dto;
    }
}
