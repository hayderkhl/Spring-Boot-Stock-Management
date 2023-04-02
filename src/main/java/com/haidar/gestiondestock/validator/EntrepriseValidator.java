package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {
    public static List<String> validate(EntrepriseDto dto) {
        List<String> errors = new ArrayList<>();

        if(dto == null) {
            errors.add("veuillez saisir le nom de l'entreprise");
            errors.add("veuillez saisir le numero de l'entreprise");
            errors.add("veuillez saisir le email de l'entreprise");
            errors.add("veuillez saisir le description de l'entreprise");
            errors.add("veuillez saisir le site de l'entreprise");
            errors.add("veuillez saisir le code fiscale de l'entreprise");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("veuillez saisir le nom de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("veuillez saisir le numero de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getEmail())){
            errors.add("veuillez saisir le email de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getDescription())){
            errors.add("veuillez saisir le description de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getSiteWeb())){
            errors.add("veuillez saisir le site de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getCodeFiscal())){
            errors.add("veuillez saisir le code fiscale de l'entreprise");
        }
        return errors;
    }
}
