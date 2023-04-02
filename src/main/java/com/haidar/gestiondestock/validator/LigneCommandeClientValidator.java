package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeClientValidator {

    public static List<String> validate(LigneCommandeClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez saisir la commande de ligne commande Client");
            errors.add("veuillez saisir la quantite de ligne commande Client");
            errors.add("veuillez saisir le prix unitaire de ligne commande Client");
            return errors;
        }
        if (dto.getCommandeClient() == null) {
            errors.add("veuillez saisir la commande de ligne commande Client");
        }
        if (dto.getQuantite() == null) {
            errors.add("veuillez saisir la quantite de ligne commande Client");
        }
        if (dto.getPrixUnitaire() == null) {
            errors.add("veuillez saisir le prix unitaire de ligne commande Client");
        }
        return errors;
    }
}
