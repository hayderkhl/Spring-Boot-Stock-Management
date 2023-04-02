package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.MvtStockDto;

import java.util.ArrayList;
import java.util.List;

public class MvtStockValidator {

    public static List<String> validate(MvtStockDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez saisir la date de mouvement");
            errors.add("veuillez saisir la quantite de mouvement");

            return errors;
        }

        if (dto.getDateMvt() ==null) {
            errors.add("veuillez saisir la date de mouvement");
        }
        if (dto.getQuantite() ==null) {
            errors.add("veuillez saisir la quantite de mouvement");
        }
        return errors;
    }
}
