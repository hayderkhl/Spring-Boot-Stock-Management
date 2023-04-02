package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

    public static List<String> validator(CategoryDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("veuillez renseigner le code de category");
            errors.add("veuillez renseigner le designation de category");
            return errors;

        }

        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("veuillez renseigner le code de category");
        }
        if (!StringUtils.hasLength(dto.getDesignation())) {
            errors.add("veuillez renseigner le designation de category");
        }

        return errors;
    }
}
