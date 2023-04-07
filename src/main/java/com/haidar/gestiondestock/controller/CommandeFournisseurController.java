package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.CommandeFournisseurApi;
import com.haidar.gestiondestock.dto.CommandeFournisseurDto;
import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.haidar.gestiondestock.model.EtatCommand;
import com.haidar.gestiondestock.service.CommandeFournisseurService;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    CommandeFournisseurService commandeFournisseurService;
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        return commandeFournisseurService.save(dto);
    }

    @Override
    public CommandeFournisseurDto findById(Integer idcommandefournisseur) {
        return commandeFournisseurService.findById(idcommandefournisseur);
    }

    @Override
    public CommandeFournisseurDto findCommandeFournisseurByCode(String codecommandefournisseur) {
        return commandeFournisseurService.findCommandeFournisseurByCode(codecommandefournisseur);
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
       return commandeFournisseurService.findAll();
    }

    @Override
    public void delete(Integer idcommandefournisseur) {
         commandeFournisseurService.delete(idcommandefournisseur);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommand etatCommande) {
        return commandeFournisseurService.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeFournisseurService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return commandeFournisseurService.updateFournisseur(idCommande, idFournisseur);
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return commandeFournisseurService.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return commandeFournisseurService.deleteArticle(idCommande, idLigneCommande);
    }
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return commandeFournisseurService.findAllLignesCommandesFournisseurByCommandeFournisseurId(idCommande);
    }
}
