package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.*;
import com.haidar.gestiondestock.model.*;
import com.haidar.gestiondestock.repository.*;
import com.haidar.gestiondestock.service.CommandeFournisseurService;
import com.haidar.gestiondestock.service.MvtStkService;
import com.haidar.gestiondestock.validator.ArticleValidator;
import com.haidar.gestiondestock.validator.CommandeClientValidator;
import com.haidar.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
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
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private MvtStkService mvtStkService;
    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                     FournisseurRepository fournisseurRepository,
                                     ArticleRepository articleRepository,
                                     LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                                          MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.mvtStkService = mvtStkService;
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
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommand etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("Letat de la commande fournisseur null");
            throw new InvalidOperationException("Impossible de modifier l'etat de commande avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommand(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        if (commandeFournisseur.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommande);
    }
    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de ligne commande est null");
            throw new InvalidOperationException("Impossible de modifier la quantite si on a deja une quantité null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
       checkIdCommande(idCommande);
       if (idFournisseur == null) {
           log.error("L'ID du fournisseur est null");
           throw new InvalidOperationException("impssible de modifier le fornisseur avec une id null",
               ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
       }
       CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
       Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
       if(fournisseurOptional.isEmpty()) {
           throw new EntityNotFoundException(
                   "Aucun fournisseur n'a ete trouver avec l ID "+ idFournisseur , ErrorCodes.FOURNISSEUR_NOT_FOUND
           );
       }
        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
        );
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
       checkIdCommande(idCommande);
       checkIdLigneCommande(idLigneCommande);
       checkIdArticle(idArticle, "new");

       CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
       Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
       if(articleOptional.isEmpty()) {
           throw new EntityNotFoundException(
                   "Aucun article n'a ete trouve avec l' ID" + idArticle, ErrorCodes.ARTICLE_NOT_FOUND
           );
       }
       List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
       if(!errors.isEmpty()) {
           throw new InvalidEntityException("Article not valid", ErrorCodes.ARTICLE_NOT_valid, errors);
       }

       LigneCommandeFournisseur ligneCommandeFournisseurToSave = ligneCommandeFournisseur.get();
       ligneCommandeFournisseurToSave.setArticle(articleOptional.get());
       ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSave);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }
    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }
    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }
    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }
    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseur.forEach(lig -> {
            effectuerEntree(lig);
        });
    }

    private void effectuerEntree(LigneCommandeFournisseur lig) {
        MvtStockDto mvtStkDto = MvtStockDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvtStock(TypeMvtStock.ENTREE)
                .sourceMvt(SourceMvtStock.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.entreeStock(mvtStkDto);
    }
}
