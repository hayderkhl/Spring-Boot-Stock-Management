package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("veuillez saisir le code de commande fournisseur");
        }
        if (dto.getDateCommande() == null) {
            errors.add("veuillez saisir la date de commande fournisseur");
        }
        return errors;
    }
}
