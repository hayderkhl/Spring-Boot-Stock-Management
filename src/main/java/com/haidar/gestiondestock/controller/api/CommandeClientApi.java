package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.model.EtatCommand;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandesclients")
public interface CommandeClientApi {
     @PostMapping(value = APP_ROOT + "/commandesclients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);
     @GetMapping(value = APP_ROOT + "/commandesclients/{idcommandeclient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idcommandeclient);
     @GetMapping(value = APP_ROOT + "/commandesclients/{codecommandeclient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable String codecommandeclient);
     @GetMapping(APP_ROOT + "/commandesclients/all")
    ResponseEntity<List<CommandeClientDto>> findAll();
     @DeleteMapping(APP_ROOT + "/commandesclients/delete/{idcommandeclient}")
    ResponseEntity delete(@PathVariable Integer idcommandeclient);
     @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idcommand}/{etatCommand}")
     ResponseEntity<CommandeClientDto> updateEtatCommand(@PathVariable Integer idcommand, @PathVariable EtatCommand etatCommand);
    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idcommand}/{idLigneCommand}/{quantite}")
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable Integer idcommand, @PathVariable Integer idLigneCommand ,@PathVariable BigDecimal quantite);
    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idcommand}/{idClient}")
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable Integer idCommand, @PathVariable Integer idClient);
    @PatchMapping(APP_ROOT + "/commandesclients/update/article/{idcommand}/{idLigneCommand}/{newIdArticle}")
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable Integer idCommand,@PathVariable Integer idLigneCommand,@PathVariable Integer newIdArticle);
    @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{idcommand}/{idLigneCommand}")
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable Integer idCommand,@PathVariable Integer idLigneCommand);
    @GetMapping(APP_ROOT + "/commandesclients/all/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

}
