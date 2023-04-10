package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.dto.LigneCommandeClientDto;
import com.haidar.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.haidar.gestiondestock.dto.LigneVenteDto;
import com.haidar.gestiondestock.model.Article;
import com.haidar.gestiondestock.model.LigneCommandeClient;
import com.haidar.gestiondestock.model.LigneCommandeFournisseur;
import com.haidar.gestiondestock.model.LigneVente;
import com.haidar.gestiondestock.repository.ArticleRepository;
import com.haidar.gestiondestock.repository.LigneCommandeClientRepository;
import com.haidar.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.haidar.gestiondestock.repository.LigneVenteRepository;
import com.haidar.gestiondestock.service.ArticleService;
import com.haidar.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private LigneVenteRepository ligneVenteRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                              LigneVenteRepository ligneVenteRepository,
                              LigneCommandeClientRepository ligneCommandeClientRepository) {
        this.articleRepository = articleRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
    }
    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("Article is not validate {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_valid);
        }
        return ArticleDto.fromEntity(articleRepository.save(
                ArticleDto.toEntity(dto)
        ));
    }

    @Override
    public ArticleDto findById(Integer id) {
        if(id == null) {
            log.error("Article is null");
            return null;
        }
        Optional<Article> article = articleRepository.findById(id);
        return Optional.of(ArticleDto.fromEntity(article.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No articl with this ID"+ id + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)) {
            log.error("Article CODE is null");
            return null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);
        return Optional.of(ArticleDto.fromEntity(article.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No articl with this ID"+ codeArticle + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(Integer idArticle) {
        return ligneVenteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return ligneCommandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueFournisseur(Integer idArticle) {
        return ligneCommandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Article is null");
            return;
        }

        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        articleRepository.deleteById(id);
    }
}
