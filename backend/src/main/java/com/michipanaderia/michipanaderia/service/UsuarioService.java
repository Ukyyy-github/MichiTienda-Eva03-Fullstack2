package com.michipanaderia.michipanaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michipanaderia.michipanaderia.DTO.UsuarioDTO;
import com.michipanaderia.michipanaderia.model.Rol;
import com.michipanaderia.michipanaderia.model.Usuario;
import com.michipanaderia.michipanaderia.repository.RolRepository;
import com.michipanaderia.michipanaderia.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public UsuarioDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Usuario no encontrado!"));
        return convertirADTO(usuario);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("¡Ya existe un usuario con ese email!");
        }
        Rol rol = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new RuntimeException("¡El rol indicado no existe!"));
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuarios(Integer id, Usuario usuario) {
        Usuario usr = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Usuario no existe en los registros!"));
        if (usuario.getNombre() != null) {
            usr.setNombre(usuario.getNombre());
        }
        if (usuario.getApellido() != null) {
            usr.setApellido(usuario.getApellido());
        }
        if (usuario.getEmail() != null) {
            if (!usr.getEmail().equals(usuario.getEmail()) && usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new RuntimeException("¡Ya existe un usuario con ese email!");
            }
            usr.setEmail(usuario.getEmail());
        }
        if (usuario.getPassword() != null) {
            usr.setPassword(usuario.getPassword());
        }
        if (usuario.getTelefono() != null) {
            usr.setTelefono(usuario.getTelefono());
        }
        if (usuario.getDireccion() != null) {
            usr.setDireccion(usuario.getDireccion());
        }
        if (usuario.getRol() != null) {
            Rol rol = rolRepository.findById(usuario.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("¡El rol indicado no existe!"));
            usr.setRol(rol);
        }
        return usuarioRepository.save(usr);
    }

    public String eliminar(Integer id) {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! usuario con ID " + id + " no existe."));
            usuarioRepository.delete(usuario);
            return "usuario '" + usuario.getNombre() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setDireccion(usuario.getDireccion());
        dto.setFechaRegistro(usuario.getFechaRegistro());

        if (usuario.getRol() != null) {
            dto.setNombreRol(usuario.getRol().getNombre());
        }

        return dto;
    }
}
