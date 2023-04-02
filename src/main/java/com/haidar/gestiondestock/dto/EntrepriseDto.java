package com.haidar.gestiondestock.dto;

import com.haidar.gestiondestock.model.Adresse;
import com.haidar.gestiondestock.model.Entreprise;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import java.util.List;
@Builder
@Data
public class EntrepriseDto {

    private Integer id;
    private String nom;
    private String description;
    @Embedded
    private Adresse adresse;
    private String codeFiscal;
    private String photo;
    private String email;
    private String numTel;
    private String siteWeb;
    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise) {
        if (entreprise == null) {
            return null;
        }

        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .description(entreprise.getDescription())
                .codeFiscal(entreprise.getCodeFiscal())
                .nom(entreprise.getNom())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .adresse(entreprise.getAdresse())
                .numTel(entreprise.getNumTel())
                .siteWeb(entreprise.getSiteWeb())
                .build();
    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto) {
        if (entrepriseDto == null) {
            return null;
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setAdresse(entrepriseDto.getAdresse());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSiteWeb(entrepriseDto.getSiteWeb());
        return entreprise;
    }

}
