package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.ProveedorDTO;
import com.michipanaderia.michipanaderia.model.Proveedor;
import com.michipanaderia.michipanaderia.repository.ProveedorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<ProveedorDTO> obtenerTodos() {
        return proveedorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ProveedorDTO buscarPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Proveedor no encontrado!"));
        return convertirADTO(proveedor);
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizarProveedores(Integer id, Proveedor proveedor) {
        Proveedor prv = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Proveedor no existe en los registros!"));
        if (proveedor.getNombreEmpresa() != null) {
            prv.setNombreEmpresa(proveedor.getNombreEmpresa());
        }
        if (proveedor.getContactoNombre() != null) {
            prv.setContactoNombre(proveedor.getContactoNombre());
        }
        if (proveedor.getTelefono() != null) {
            prv.setTelefono(proveedor.getTelefono());
        }
        if (proveedor.getEmail() != null) {
            prv.setEmail(proveedor.getEmail());
        }
        if (proveedor.getInsumoPrincipal() != null) {
            prv.setInsumoPrincipal(proveedor.getInsumoPrincipal());
        }
        return proveedorRepository.save(prv);
    }

    public String eliminar(Integer id) {
        try {
            Proveedor proveedor = proveedorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! proveedor con ID " + id + " no existe."));
            proveedorRepository.delete(proveedor);
            return "proveedor '" + proveedor.getNombreEmpresa() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private ProveedorDTO convertirADTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setNombreEmpresa(proveedor.getNombreEmpresa());
        dto.setContactoNombre(proveedor.getContactoNombre());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        dto.setInsumoPrincipal(proveedor.getInsumoPrincipal());
        return dto;
    }
}
