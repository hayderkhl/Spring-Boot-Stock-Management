package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.CommandeClientApi;
import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.model.EtatCommand;
import com.haidar.gestiondestock.service.CommandeClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeClientController implements CommandeClientApi {

    public CommandeClientController(CommandeClientService clientService) {
        this.clientService = clientService;
    }

    CommandeClientService clientService;
    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {
        return ResponseEntity.ok(clientService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer id) {
        return ResponseEntity.ok(clientService.findById(id));
       // OR :  return ResponseEntity.status(HttpStatus.OK).body(clientService.findById(id));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findByCode(String code) {
        return ResponseEntity.ok(clientService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @Override
    public ResponseEntity delete(Integer id) {
        clientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateEtatCommand(Integer idcommand, EtatCommand etatCommand) {
        return ResponseEntity.ok(clientService.updateEtatCommand(idcommand, etatCommand));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateQuantiteCommande(Integer idcommand, Integer idLigneCommand, BigDecimal quantite) {
        return ResponseEntity.ok(clientService.updateQuantiteCommande(idcommand, idLigneCommand, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateClient(Integer idCommand, Integer idClient) {
        return ResponseEntity.ok(clientService.updateClient(idCommand, idClient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateArticle(Integer idCommand, Integer idLigneCommand, Integer newIdArticle) {
        return ResponseEntity.ok(clientService.updateArticle(idCommand, idLigneCommand, newIdArticle));
    }

    @Override
    public ResponseEntity<CommandeClientDto> deleteArticle(Integer idCommand, Integer idLigneCommand) {
        return ResponseEntity.ok(clientService.deleteArticle(idCommand, idLigneCommand));
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ResponseEntity.ok(clientService.findAllLignesCommandesClientByCommandeClientId(idCommande));
    }
}
