package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.LigneVenteDto;

import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {

    public static List<String> validate(LigneVenteDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez saisir la quantité");
            errors.add("veuillez saisir le prix unitaire");
        }
        if (dto.getQuantite() == null) {
            errors.add("veuillez saisir la quantité");
        }
        if (dto.getPrixUnitaire() == null) {
            errors.add("veuillez saisir le prix unitaire");
        }
        if (dto.getArticle() == null) {
            errors.add("veuillez saisir un ou des articles");
        }
        return errors;
    }
}
