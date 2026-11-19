package com.michipanaderia.michipanaderia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boletas")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    private Integer id;

    @NotBlank(message = "El número de boleta es obligatorio")
    @Size(max = 50, message = "El número de boleta no puede tener más de 50 caracteres")
    @Column(name = "numero_boleta", nullable = false, unique = true, length = 50)
    private String numeroBoleta;

    @Column(name = "fecha_emision", insertable = false, updatable = false)
    private LocalDateTime fechaEmision;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", message = "El subtotal no puede ser negativo")
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @NotNull(message = "El IVA es obligatorio")
    @DecimalMin(value = "0.0", message = "El IVA no puede ser negativo")
    @Column(name = "iva", nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", message = "El total no puede ser negativo")
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 30, message = "El estado no puede tener más de 30 caracteres")
    @Column(name = "estado", nullable = false, length = 30)
    private String estado = "PAGADO";

    @NotBlank(message = "El tipo de entrega es obligatorio")
    @Size(max = 30, message = "El tipo de entrega no puede tener más de 30 caracteres")
    @Column(name = "tipo_entrega", nullable = false, length = 30)
    private String tipoEntrega = "RETIRO_LOCAL";

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "El local es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_local", nullable = false)
    private Local local;
}
