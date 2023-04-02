package com.haidar.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haidar.gestiondestock.model.Article;
import com.haidar.gestiondestock.model.Category;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class CategoryDto {

    private Integer id;
    private String code;
    private String designation;
    private Integer idEntreprise;
    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
            //TODO throw an exception
        }
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .idEntreprise(category.getIdEntreprise())
                .designation(category.getDesignation())
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
                    // TODO throw an exception
        }
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());

        return category;
    }
}
