package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.CommandeFournisseurDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.haidar.gestiondestock.model.*;
import com.haidar.gestiondestock.repository.*;
import com.haidar.gestiondestock.service.CommandeFournisseurService;
import com.haidar.gestiondestock.validator.CommandeClientValidator;
import com.haidar.gestiondestock.validator.CommandeFournisseurValidator;
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
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                     FournisseurRepository fournisseurRepository,
                                     ArticleRepository articleRepository,
                                     LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("CommandeFournisseur  n'est pas valide  {}", dto);
            throw new InvalidEntityException("L'CommandeFournisseur n'est pas valide", ErrorCodes.ARTICLE_NOT_valid);
        }

        //we have to verify if the client exist in the DB
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (!fournisseur.isPresent()) {
            log.warn("this Client ID does not exist in Data Base", dto.getFournisseur().getId());
            throw new EntityNotFoundException("No Client begons to this ID", ErrorCodes.CLIENT_NOT_FOUND);
        }

        //we have to verify that the artical in the ligne commande client exist in DB
        List<String> articleErrors = new ArrayList<>();
        if(dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmClt -> {
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

        CommandeFournisseur savedCmClt = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));
        if(dto.getLigneCommandeFournisseurs() != null) {

            dto.getLigneCommandeFournisseurs().forEach(ligCmClt -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmClt);
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmClt);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }

        return CommandeFournisseurDto.fromEntity(savedCmClt);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null) {
            log.error("CommandeClient is null");
            return null;
        }
        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findById(id);
        return Optional.of(CommandeFournisseurDto.fromEntity(commandeFournisseur.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Fournisseur with this ID "+ id + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public CommandeFournisseurDto findCommandeFournisseurByCode(String code) {
        if(code == null) {
            log.error("CommandeClient is null");
            return null;
        }
        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findCommandeFournisseurByCode(code);
        return Optional.of(CommandeFournisseurDto.fromEntity(commandeFournisseur.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Client with this CODE "+ code + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Client is null");
            return;
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommand etatCommande) {
      return null;
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return null;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return null;
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return null;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return null;
    }
}
