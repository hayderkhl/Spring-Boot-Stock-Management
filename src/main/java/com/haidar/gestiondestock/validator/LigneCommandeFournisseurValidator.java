package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {

    public static List<String> validate(LigneCommandeFournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez saisir la commande de ligne commande fournisseur");
            errors.add("veuillez saisir la quantite de ligne commande fournisseur");
            errors.add("veuillez saisir le prix unitaire de ligne commande fournisseur");
            return errors;
        }
        if(dto.getCommandeFournisseur() == null) {
            errors.add("veuillez saisir la commande de ligne commande fournisseur");
        }
        if(dto.getQuantite() == null) {
            errors.add("veuillez saisir la quantite de ligne commande fournisseur");
        }
        if(dto.getPrixUnitaire() == null) {
            errors.add("veuillez saisir le prix unitaire de ligne commande fournisseur");
        }
        return errors;
    }
}
