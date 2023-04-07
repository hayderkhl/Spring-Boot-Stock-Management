package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.CommandeFournisseurDto;
import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.haidar.gestiondestock.model.EtatCommand;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandesfournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(value = APP_ROOT + "/commandesfournisseurs/create")
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @GetMapping(value = APP_ROOT + "/commandesfournisseurs/{idcommandefournisseur}")
    CommandeFournisseurDto findById(@PathVariable Integer idcommandefournisseur);
    @GetMapping(value = APP_ROOT + "/commandesfournisseurs/{codecommandefournisseur}")
    CommandeFournisseurDto findCommandeFournisseurByCode(@PathVariable String codecommandefournisseur);

    @GetMapping(value = APP_ROOT + "/commandesfournisseurs/all")
    List<CommandeFournisseurDto> findAll();


    @DeleteMapping(value = APP_ROOT + "/commandesfournisseurs/delete/{idcommandefournisseur}")
    void delete(@PathVariable Integer idcommandefournisseur);

    @PatchMapping(APP_ROOT + "/update/etat/{idCommande}/{etatCommande}")
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommand etatCommande);

    @PatchMapping(APP_ROOT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                  @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/update/fournisseur/{idCommande}/{idFournisseur}")
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @PatchMapping(APP_ROOT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande") Integer idCommande,
                                         @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(APP_ROOT + "/delete/article/{idCommande}/{idLigneCommande}")
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(APP_ROOT + "/lignesCommande/{idCommande}")
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

}
