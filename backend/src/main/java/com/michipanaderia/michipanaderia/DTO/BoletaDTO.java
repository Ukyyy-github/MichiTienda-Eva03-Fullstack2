package com.michipanaderia.michipanaderia.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletaDTO {
    private Integer id;
    private String numeroBoleta;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private String estado;
    private String tipoEntrega;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idLocal;
    private String nombreLocal;
}
