package com.michipanaderia.michipanaderia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String comunaCiudad;
    private String telefono;
    private String horarioAtencion;
}
