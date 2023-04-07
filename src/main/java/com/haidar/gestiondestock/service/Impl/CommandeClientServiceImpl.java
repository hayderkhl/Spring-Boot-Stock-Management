package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.dto.ClientDto;
import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.model.*;
import com.haidar.gestiondestock.repository.ArticleRepository;
import com.haidar.gestiondestock.repository.ClientRepository;
import com.haidar.gestiondestock.repository.CommandeClientRepository;
import com.haidar.gestiondestock.repository.LigneCommandeClientRepository;
import com.haidar.gestiondestock.service.CommandeClientService;
import com.haidar.gestiondestock.validator.ArticleValidator;
import com.haidar.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;
    LigneCommandeClientRepository ligneCommandeClientRepository;
    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,
                                     ClientRepository clientRepository,
                                     ArticleRepository articleRepository,
                                     LigneCommandeClientRepository ligneCommandeClientRepository) {
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        List<String> errors = CommandeClientValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("CommandeClient  n'est pas valide  {}", dto);
            throw new InvalidEntityException("L'CommandeClient n'est pas valide", ErrorCodes.ARTICLE_NOT_valid);
        }

        // if "dto.getId() != null" that's mean we are modifying the command
        //we cannot make any modification here we will do another function to update the command status
        if(dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        //we have to verify if the client exist in the DB
        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (!client.isPresent()) {
            log.warn("this Client ID does not exist in Data Base", dto.getClient().getId());
            throw new EntityNotFoundException("No Client begons to this ID", ErrorCodes.CLIENT_NOT_FOUND);
        }

        //we have to verify that the artical in the ligne commande client exist in DB
        List<String> articleErrors = new ArrayList<>();
        if(dto.getLigneCommandeClients() != null) {
            dto.getLigneCommandeClients().forEach(ligCmClt -> {
                if (ligCmClt.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmClt.getArticle().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("l'article de l'id"+ ligCmClt.getArticle().getId() + "n'existe pas");
                    }
                } else {
                        articleErrors.add("impossible to add a command with a null Artical");
                }
            });
        }

       CommandeClient savedCmClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));
        if(dto.getLigneCommandeClients() != null) {

            dto.getLigneCommandeClients().forEach(ligCmClt -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmClt);
                ligneCommandeClient.setCommandeClient(savedCmClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);
            });
        }

        return CommandeClientDto.fromEntity(savedCmClt);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null) {
            log.error("CommandeClient is null");
            return null;
        }
        Optional<CommandeClient> commandeClient = commandeClientRepository.findById(id);
        return Optional.of(CommandeClientDto.fromEntity(commandeClient.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Client with this ID "+ id + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if(code == null) {
            log.error("CommandeClient is null");
            return null;
        }
        Optional<CommandeClient> commandeClient = commandeClientRepository.findCommandeClientByCode(code);
        return Optional.of(CommandeClientDto.fromEntity(commandeClient.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Client with this CODE "+ code + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Client is null");
            return;
        }

        commandeClientRepository.deleteById(id);
    }
// the method checkIdCommand is a refactoring we can do it with other function to let our code more readable
    private void checkIdCommand(Integer idCommand) {
        if(idCommand == null) {
            log.error("Command Client est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un id null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }
    @Override
    public CommandeClientDto updateEtatCommand(Integer idCommand, EtatCommand etatCommand) {
        checkIdCommand(idCommand);

        if(!StringUtils.hasLength(String.valueOf(etatCommand))) {
            log.error("L'etat Command Client est null");
            throw new InvalidOperationException("Impossible de modifier d'etat de la commande avec un id null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = findById(idCommand);
        if(commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Ipossible de modifier la commande l'orsqu'elle est livree",
                        ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        commandeClient.setEtatCommand(etatCommand);

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommand, Integer idLigneCommand, BigDecimal quantité) {
        checkIdCommand(idCommand);

        if(idLigneCommand == null) {
            log.error("L'ID de la ligne Command Client est null");
            throw new InvalidOperationException("Impossible de modifier la quantite avec un id null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        if(quantité == null || quantité.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne Command Client est null");
            throw new InvalidOperationException("Impossible de modifier la quantite avec une quantité Zero ou null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = findById(idCommand);
        if(commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Ipossible de modifier la commande l'orsqu'elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommand);

        if(ligneCommandeClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No Ligne de commande par cette ID "+ idLigneCommand
                    , ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND);
        }
        // after the verification we do the modification here

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantité);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommand, Integer idClient) {

       checkIdCommand(idCommand);

        if(idClient == null) {
            log.error("L'ID Client est null");
            throw new InvalidOperationException("Impossible de modifier le client avec idClient null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = findById(idCommand);
        if(commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Ipossible de modifier la commande l'orsqu'elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No client par cette ID "+ idClient
                    , ErrorCodes.CLIENT_NOT_FOUND);
        }
        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommand, Integer idLigneCommand, Integer newIdArticle) {

       checkIdCommand(idCommand);

        if(idLigneCommand == null) {
            log.error("L'ID Client est null");
            throw new InvalidOperationException("Impossible de modifier le client avec idClient null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        if(newIdArticle == null) {
            log.error("L'ID nouvel article est null");
            throw new InvalidOperationException("Impossible de modifier l'article avec un nouvel idArticle null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = findById(idCommand);
        if(commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Ipossible de modifier la commande l'orsqu'elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<LigneCommandeClient> ligneCommandeClient = ligneCommandeClientRepository.findById(idLigneCommand);

        Optional<Article> articleOptional = articleRepository.findById(newIdArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No article par cette ID "+ newIdArticle
                    , ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article not valid", ErrorCodes.ARTICLE_NOT_valid);
        }

        LigneCommandeClient ligneCommandeClientToSave = ligneCommandeClient.get();
        ligneCommandeClientToSave.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSave);

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommand, Integer idLigneDeCommand) {
        checkIdCommand(idCommand);

        if(idLigneDeCommand == null) {
            log.error("L'ID Client est null");
            throw new InvalidOperationException("Impossible de modifier le client avec idClient null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = findById(idCommand);
        if(commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Ipossible de modifier la commande l'orsqu'elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
       //just to check ligne command client
        ligneCommandeClientRepository.findById(idLigneDeCommand);
        ligneCommandeClientRepository.deleteById(idLigneDeCommand);

        return commandeClient;
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

}
