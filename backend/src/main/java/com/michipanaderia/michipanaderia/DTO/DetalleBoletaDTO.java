package com.michipanaderia.michipanaderia.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleBoletaDTO {
    private Integer id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotalLinea;
    private Integer idBoleta;
    private String numeroBoleta;
    private Integer idProducto;
    private String nombreProducto;
}
