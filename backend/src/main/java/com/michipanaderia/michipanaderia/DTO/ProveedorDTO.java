package com.michipanaderia.michipanaderia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDTO {
    private Integer id;
    private String nombreEmpresa;
    private String contactoNombre;
    private String telefono;
    private String email;
    private String insumoPrincipal;
}
