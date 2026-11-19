package com.michipanaderia.michipanaderia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer id;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre de la empresa debe tener entre 2 y 150 caracteres")
    @Column(name = "nombre_empresa", nullable = false, length = 150)
    private String nombreEmpresa;

    @Size(max = 100, message = "El nombre de contacto no puede tener más de 100 caracteres")
    @Column(name = "contacto_nombre", length = 100)
    private String contactoNombre;

    @Size(max = 20, message = "El teléfono no puede tener más de 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no puede tener más de 150 caracteres")
    @Column(name = "email", length = 150)
    private String email;

    @Size(max = 150, message = "El insumo principal no puede tener mas de 150 caracteres")
    @Column(name = "insumo_principal", length = 150)
    private String insumoPrincipal;
}
