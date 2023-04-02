package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.model.Article;
import com.haidar.gestiondestock.model.Client;
import com.haidar.gestiondestock.model.CommandeClient;
import com.haidar.gestiondestock.model.LigneCommandeClient;
import com.haidar.gestiondestock.repository.ArticleRepository;
import com.haidar.gestiondestock.repository.ClientRepository;
import com.haidar.gestiondestock.repository.CommandeClientRepository;
import com.haidar.gestiondestock.repository.LigneCommandeClientRepository;
import com.haidar.gestiondestock.service.CommandeClientService;
import com.haidar.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
