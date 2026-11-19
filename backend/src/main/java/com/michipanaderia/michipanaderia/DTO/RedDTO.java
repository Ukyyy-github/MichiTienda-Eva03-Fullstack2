package com.michipanaderia.michipanaderia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedDTO {
    private Integer id;
    private String plataforma;
    private String url;
    private String usuarioHandle;
}
