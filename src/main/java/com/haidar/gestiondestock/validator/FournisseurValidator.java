package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.ClientDto;
import com.haidar.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validator(FournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez renseigner le prenom de fournisseur");
            errors.add("veuillez renseigner le mail de fournisseur");
            errors.add("veuillez renseigner le numero de fournisseur");
            errors.add("veuillez renseigner le nom de fournisseur");
            return errors;

        }
        if (!StringUtils.hasLength(dto.getPrenom())) {
            errors.add("veuillez renseigner le prenom de fournisseur");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("veuillez renseigner le mail de fournisseur");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("veuillez renseigner le numero de fournisseur");
        }
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("veuillez renseigner le nom de fournisseur");
        }
        return errors;
    }
}
