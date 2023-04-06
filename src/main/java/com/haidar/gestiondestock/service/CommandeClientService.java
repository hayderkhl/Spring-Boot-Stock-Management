package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.dto.CommandeClientDto;
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
}
