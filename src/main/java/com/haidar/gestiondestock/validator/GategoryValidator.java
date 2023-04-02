package com.haidar.gestiondestock.validator;

import com.haidar.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class GategoryValidator {

    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>();

        if (categoryDto == null || !StringUtils.hasLength(categoryDto.getCode())) {
              errors.add("veuillez renseigner le code de la category");
        }
        return errors;
    }
}
