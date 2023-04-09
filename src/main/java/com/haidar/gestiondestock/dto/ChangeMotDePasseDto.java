package com.haidar.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangeMotDePasseDto {

    private Integer id;

    private String motDePasse;

    private String confirmMotDePasse;

}
