package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.haidar.gestiondestock.dto.LigneVenteDto;
import com.haidar.gestiondestock.model.LigneVente;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);
    ArticleDto findById(Integer id);
    ArticleDto findByCodeArticle(String codeArticle);
    List<ArticleDto> findAll();
    List<LigneVenteDto> findHistoriqueVente(Integer idArticle);
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);
    List<LigneCommandeFournisseurDto> findHistoriqueFournisseur(Integer idArticle);
    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);
    void delete(Integer id);

}
