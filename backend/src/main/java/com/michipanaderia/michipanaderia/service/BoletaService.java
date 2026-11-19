package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.BoletaDTO;
import com.michipanaderia.michipanaderia.model.Boleta;
import com.michipanaderia.michipanaderia.model.Local;
import com.michipanaderia.michipanaderia.model.Usuario;
import com.michipanaderia.michipanaderia.repository.BoletaRepository;
import com.michipanaderia.michipanaderia.repository.LocalRepository;
import com.michipanaderia.michipanaderia.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LocalRepository localRepository;

    public List<BoletaDTO> obtenerTodos() {
        return boletaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public BoletaDTO buscarPorId(Integer id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Boleta no encontrada!"));
        return convertirADTO(boleta);
    }

    public List<BoletaDTO> buscarPorUsuario(Integer idUsuario) {
        return boletaRepository.findByUsuarioId(idUsuario).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Boleta guardarBoleta(Boleta boleta) {
        if (boletaRepository.existsByNumeroBoleta(boleta.getNumeroBoleta())) {
            throw new RuntimeException("¡Ya existe una boleta con ese número!");
        }
        Usuario usuario = usuarioRepository.findById(boleta.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("¡El usuario indicado no existe!"));
        Local local = localRepository.findById(boleta.getLocal().getId())
                .orElseThrow(() -> new RuntimeException("¡El local indicado no existe!"));
        boleta.setUsuario(usuario);
        boleta.setLocal(local);
        return boletaRepository.save(boleta);
    }

    public Boleta actualizarBoletas(Integer id, Boleta boleta) {
        Boleta blt = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Boleta no existe en los registros!"));
        if (boleta.getNumeroBoleta() != null) {
            if (!blt.getNumeroBoleta().equals(boleta.getNumeroBoleta()) && boletaRepository.existsByNumeroBoleta(boleta.getNumeroBoleta())) {
                throw new RuntimeException("¡Ya existe una boleta con ese número!");
            }
            blt.setNumeroBoleta(boleta.getNumeroBoleta());
        }
        if (boleta.getSubtotal() != null) {
            blt.setSubtotal(boleta.getSubtotal());
        }
        if (boleta.getIva() != null) {
            blt.setIva(boleta.getIva());
        }
        if (boleta.getTotal() != null) {
            blt.setTotal(boleta.getTotal());
        }
        if (boleta.getEstado() != null) {
            blt.setEstado(boleta.getEstado());
        }
        if (boleta.getTipoEntrega() != null) {
            blt.setTipoEntrega(boleta.getTipoEntrega());
        }
        if (boleta.getUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(boleta.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("¡El usuario indicado no existe!"));
            blt.setUsuario(usuario);
        }
        if (boleta.getLocal() != null) {
            Local local = localRepository.findById(boleta.getLocal().getId())
                    .orElseThrow(() -> new RuntimeException("¡El local indicado no existe!"));
            blt.setLocal(local);
        }
        return boletaRepository.save(blt);
    }

    public String eliminar(Integer id) {
        try {
            Boleta boleta = boletaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! boleta con ID " + id + " no existe."));
            boletaRepository.delete(boleta);
            return "boleta '" + boleta.getNumeroBoleta() + "' ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private BoletaDTO convertirADTO(Boleta boleta) {
        BoletaDTO dto = new BoletaDTO();
        dto.setId(boleta.getId());
        dto.setNumeroBoleta(boleta.getNumeroBoleta());
        dto.setFechaEmision(boleta.getFechaEmision());
        dto.setSubtotal(boleta.getSubtotal());
        dto.setIva(boleta.getIva());
        dto.setTotal(boleta.getTotal());
        dto.setEstado(boleta.getEstado());
        dto.setTipoEntrega(boleta.getTipoEntrega());

        if (boleta.getUsuario() != null) {
            dto.setIdUsuario(boleta.getUsuario().getId());
            dto.setNombreUsuario(boleta.getUsuario().getNombre());
        }
        if (boleta.getLocal() != null) {
            dto.setIdLocal(boleta.getLocal().getId());
            dto.setNombreLocal(boleta.getLocal().getNombre());
        }

        return dto;
    }
}
