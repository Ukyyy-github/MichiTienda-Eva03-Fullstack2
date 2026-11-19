package com.michipanaderia.michipanaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michipanaderia.michipanaderia.DTO.AuthResponse;
import com.michipanaderia.michipanaderia.DTO.LoginRequest;
import com.michipanaderia.michipanaderia.DTO.RefreshRequest;
import com.michipanaderia.michipanaderia.DTO.RegisterRequest;
import com.michipanaderia.michipanaderia.model.Rol;
import com.michipanaderia.michipanaderia.model.Usuario;
import com.michipanaderia.michipanaderia.repository.RolRepository;
import com.michipanaderia.michipanaderia.repository.UsuarioRepository;
import com.michipanaderia.michipanaderia.security.JwtUtil;
import com.michipanaderia.michipanaderia.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login, registro y renovación de token")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Iniciar sesión", description = "Valida email y contraseña y devuelve un token de acceso y uno de renovación")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Email o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("¡Usuario no encontrado!"));
        return new ResponseEntity<>(crearRespuesta(usuario), HttpStatus.OK);
    }

    @Operation(summary = "Registrar cliente", description = "Crea un usuario nuevo siempre con rol ROLE_CLIENTE y la contraseña encriptada")
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterRequest request) {
        try {
            Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                    .orElseThrow(() -> new RuntimeException("¡No existe el rol ROLE_CLIENTE!"));

            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            usuario.setTelefono(request.getTelefono());
            usuario.setDireccion(request.getDireccion());
            usuario.setRol(rolCliente);

            usuarioService.guardarUsuario(usuario);
            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Renovar token", description = "Recibe el token de renovación y devuelve un par de tokens nuevo")
    @PostMapping("/refresh")
    public ResponseEntity<?> renovar(@Valid @RequestBody RefreshRequest request) {
        String token = request.getRefreshToken();
        if (!jwtUtil.validarToken(token) || !jwtUtil.esRefreshToken(token)) {
            return new ResponseEntity<>("Refresh token inválido o expirado", HttpStatus.UNAUTHORIZED);
        }
        Usuario usuario = usuarioRepository.findByEmail(jwtUtil.extraerEmail(token)).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("Refresh token inválido o expirado", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(crearRespuesta(usuario), HttpStatus.OK);
    }

    private AuthResponse crearRespuesta(Usuario usuario) {
        String rol = usuario.getRol().getNombre();
        return new AuthResponse(
                jwtUtil.generarAccessToken(usuario.getEmail(), rol),
                jwtUtil.generarRefreshToken(usuario.getEmail(), rol),
                "Bearer",
                usuario.getEmail(),
                rol);
    }
}