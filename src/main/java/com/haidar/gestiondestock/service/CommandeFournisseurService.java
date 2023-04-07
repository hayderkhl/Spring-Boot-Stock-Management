package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.model.EtatCommand;
import com.haidar.gestiondestock.dto.CommandeFournisseurDto;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto dto);
    CommandeFournisseurDto findById(Integer id);
    CommandeFournisseurDto findCommandeFournisseurByCode(String code);
    List<CommandeFournisseurDto> findAll();
    void delete(Integer id);
    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommand etatCommande);
    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);
    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);
    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);
    // Delete article ==> delete LigneCommandeFournisseur
    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);
}
