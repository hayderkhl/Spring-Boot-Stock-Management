package com.haidar.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class changeMotDePasseDto {

    private Integer id;

    private String motDePasse;

    private String confirmMotDePasse;

}
