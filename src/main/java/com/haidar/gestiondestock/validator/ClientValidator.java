package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validator(ClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez renseigner le pre-nom de client");
            errors.add("veuillez renseigner le mail de client");
            errors.add("veuillez renseigner le number de client");
            errors.add("veuillez renseigner le nom de client");
            return errors;

        }

        if (!StringUtils.hasLength(dto.getPrenom())) {
            errors.add("veuillez renseigner le pre-nom de client");
        }
        if (!StringUtils.hasLength(dto.getMail())) {
            errors.add("veuillez renseigner le mail de client");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("veuillez renseigner le number de client");
        }
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("veuillez renseigner le nom de client");
        }
        return errors;
    }
}
