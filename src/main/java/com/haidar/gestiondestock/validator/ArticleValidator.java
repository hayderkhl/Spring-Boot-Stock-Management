package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez renseigner le designation de l'article");
            errors.add("veuillez renseigner le prix unitaire de l'article");
            errors.add("veuillez renseigner le code Tva de l'article");
            errors.add("veuillez renseigner le code de l'article");
            errors.add("veuillez renseigner le prix ttc de l'article");
            errors.add("veuillez selectionner la category");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getDesignation())) {
            errors.add("veuillez renseigner le designation de l'article");
        }
        if (dto.getPrixUnitaire() == null) {
            errors.add("veuillez renseigner le prix unitaire de l'article");
        }
        if (dto.getTauxTva() == null) {
            errors.add("veuillez renseigner le code Tva de l'article");
        }
        if (!StringUtils.hasLength(dto.getCodeArticle())) {
            errors.add("veuillez renseigner le code de l'article");
        }
        if (dto.getPrixUnitaireTTc() == null) {
            errors.add("veuillez renseigner le prix ttc de l'article");
        }
        if (dto.getCategory() == null) {
            errors.add("veuillez selectionner la category");
        }
        return errors;
    }
}
