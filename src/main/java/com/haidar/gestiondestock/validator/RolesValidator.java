package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.RolesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RolesValidator {

    public static List<String> validate(RolesDto dto) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasLength(dto.getRoleName())) {
            errors.add("veuillez saisir le nom de role");
        }
        return errors;
    }
}
