package com.haidar.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haidar.gestiondestock.model.Article;
import com.haidar.gestiondestock.model.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ArticleDto {

    private Integer id;
    private String codeArticle;
    private String designation;
    private BigDecimal prixUnitaire;
    private BigDecimal tauxTva;
    private BigDecimal prixUnitaireTTc;

    private CategoryDto category;

    @JsonIgnore
    private List<LigneVenteDto> ligneVenteDtos;

    private Integer idEntreprise;

    public static ArticleDto fromEntity(Article article) {
        if (article == null) {
            return null;
        }

        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaire(article.getPrixUnitaire())
                .prixUnitaireTTc(article.getPrixUnitaireTTc())
                .tauxTva(article.getTauxTva())
                .idEntreprise(article.getIdEntreprise())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .build();
    }

    public static Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaire(articleDto.getPrixUnitaire());
        article.setPrixUnitaireTTc(articleDto.getPrixUnitaireTTc());
        article.setTauxTva(articleDto.getTauxTva());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
        article.setIdEntreprise(articleDto.getIdEntreprise());

        return article;
    }


}
