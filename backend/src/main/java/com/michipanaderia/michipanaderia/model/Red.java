package com.michipanaderia.michipanaderia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "redes")
public class Red {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_red")
    private Integer id;

    @NotBlank(message = "La plataforma es obligatoria")
    @Size(min = 2, max = 50, message = "La plataforma debe tener entre 2 y 50 caracteres")
    @Column(name = "plataforma", nullable = false, length = 50)
    private String plataforma;

    @NotBlank(message = "La URL es obligatoria")
    @Size(min = 5, max = 255, message = "La URL debe tener entre 5 y 255 caracteres")
    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Size(max = 100, message = "El usuario no puede tener más de 100 caracteres")
    @Column(name = "usuario_handle", length = 100)
    private String usuarioHandle;
}
