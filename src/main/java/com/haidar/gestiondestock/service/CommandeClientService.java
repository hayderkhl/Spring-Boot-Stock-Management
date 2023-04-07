package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.model.EtatCommand;
import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto dto);
    CommandeClientDto findById(Integer id);
    CommandeClientDto findByCode(String code);
    List<CommandeClientDto> findAll();
    void delete(Integer id);
    CommandeClientDto updateEtatCommand(Integer idCommand, EtatCommand etatCommand);
    CommandeClientDto updateQuantiteCommande(Integer idCommand, Integer idLigneCommand, BigDecimal quantité);
    CommandeClientDto updateClient(Integer idCommand, Integer idClient);
    CommandeClientDto updateArticle(Integer idCommand, Integer idLigneCommand, Integer newIdArticle);
    CommandeClientDto deleteArticle(Integer idCommand , Integer idLigneDeCommand);
    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);

}
