package com.haidar.gestiondestock.dto;

import com.haidar.gestiondestock.model.Adresse;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AdressDto {

    private Integer id;
    private String adresse1;
    private String adresse2;
    private String ville;
    private String pays;

    public static AdressDto fromEntity(Adresse adresse) {
        if (adresse == null) {
            return null;
        }
        return AdressDto.builder()
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .ville(adresse.getVille())
                .pays(adresse.getPays())
                .build();
    }
    public static Adresse toEntity(AdressDto adressDto) {
        if(adressDto == null) {
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setAdresse1(adressDto.getAdresse1());
        adresse.setAdresse2(adressDto.getAdresse2());
        adresse.setVille(adressDto.getVille());
        adresse.setPays(adressDto.getPays());
        return adresse;
    }
}
