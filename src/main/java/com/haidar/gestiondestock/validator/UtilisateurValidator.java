package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.CategoryDto;
import com.haidar.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasLength(utilisateurDto.getPrenom())) {
            errors.add("veuillez renseigner le prenom de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getNom())) {
            errors.add("veuillez renseigner le nom de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getEmail())) {
            errors.add("veuillez renseigner le mail de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getMotDePasse())) {
            errors.add("veuillez renseigner le prenom de l'utilisateur");
        }
       if (utilisateurDto.getAdresse() == null) {
           errors.add("veuillez renseigner l'address de l'utilisateur");
       } else {
           if (!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())) {
               errors.add("veuillez renseigner le adress1 de l'utilisateur");
           }
           if (!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())) {
               errors.add("veuillez renseigner le ville de l'utilisateur");
           }
           if (!StringUtils.hasLength(utilisateurDto.getAdresse().getPays())) {
               errors.add("veuillez renseigner le pays de l'utilisateur");
           }
       }
        if (utilisateurDto.getDateDeNaissance() == null) {
            errors.add("veuillez renseigner la Date De Naissance de l'utilisateur");
        }

        return errors;
    }
}
