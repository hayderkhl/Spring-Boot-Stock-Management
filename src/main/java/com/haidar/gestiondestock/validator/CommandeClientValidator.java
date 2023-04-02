package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("veuillez saisir le code de commande client");
        }
        if (dto.getDateCommande() == null) {
            errors.add("veuillez saisir la date de commande client");
        }

        return errors;
    }
}
