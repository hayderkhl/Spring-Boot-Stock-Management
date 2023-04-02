package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VentesValidator {

    public static List<String> validate(VentesDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez saisir la date de vente");
            errors.add("veuillez saisir la code de vente");
            errors.add("veuiller saisir un commentaire");
            return errors;
        }
        if (dto.getDateVente() == null) {
            errors.add("veuillez saisir la date de vente");
        }
        if (dto.getCode() == null) {
            errors.add("veuillez saisir la code de vente");
        }
        if(!StringUtils.hasLength(dto.getCommentaire())) {
            errors.add("veuiller saisir un commentaire");
        }
        return errors;
    }
}
