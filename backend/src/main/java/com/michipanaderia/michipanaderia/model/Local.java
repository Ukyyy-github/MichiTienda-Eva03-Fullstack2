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
@Table(name = "locales")
public class Local {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @NotBlank(message = "La comuna o ciudad es obligatoria")
    @Size(min = 2, max = 100, message = "La comuna o ciudad debe tener entre 2 y 100 caracteres")
    @Column(name = "comuna_ciudad", nullable = false, length = 100)
    private String comunaCiudad;

    @Size(max = 20, message = "El teléfono no puede tener más de 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Size(max = 150, message = "El horario no puede tener más de 150 caracteres")
    @Column(name = "horario_atencion", length = 150)
    private String horarioAtencion;
}
