package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.dto.LigneVenteDto;
import com.haidar.gestiondestock.dto.MvtStockDto;
import com.haidar.gestiondestock.dto.VentesDto;
import com.haidar.gestiondestock.model.*;
import com.haidar.gestiondestock.repository.ArticleRepository;
import com.haidar.gestiondestock.repository.LigneVenteRepository;
import com.haidar.gestiondestock.repository.VentesRepository;
import com.haidar.gestiondestock.service.MvtStkService;
import com.haidar.gestiondestock.service.VenteService;
import com.haidar.gestiondestock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VenteServiceImpl implements VenteService {
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService;
    @Autowired
    public VenteServiceImpl(VentesRepository ventesRepository, LigneVenteRepository ligneVenteRepository,
                            ArticleRepository articleRepository, MvtStkService mvtStkService) {
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VentesValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Vente is not valid {}", dto);
            throw new InvalidEntityException("Le'Vente n'est pas valide", ErrorCodes.ARTICLE_NOT_valid);
        }
        List<String> articleErrors = new ArrayList<>();
        dto.getLigneVenteDtos().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()) {
                articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId() + " n'est pas ete dans la DB");
            }
        });

        if (!articleErrors.isEmpty()) {
            log.error("one or more article not found", errors);
            throw new InvalidEntityException("one or more article not found", ErrorCodes.VENTE_NOT_FOUND);
        }

        Ventes saveVentes = ventesRepository.save(VentesDto.toEntity(dto));

        dto.getLigneVenteDtos().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVentes(saveVentes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });
        return VentesDto.fromEntity(saveVentes);
    }

    @Override
    public VentesDto findById(Integer id) {
        if(id == null) {
            log.error("Vente is null");
            return null;
        }
        Optional<Ventes> vente = ventesRepository.findById(id);
        return Optional.of(VentesDto.fromEntity(vente.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No vente with this ID"+ id + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public VentesDto findVenteByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("Vente CODE is null");
            return null;
        }
        Optional<Ventes> ventes = ventesRepository.findVentesByCode(code);
        return Optional.of(VentesDto.fromEntity(ventes.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No articl with this ID"+ code + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Client is null");
            return;
        }

        ventesRepository.deleteById(id);

    }

    private void updateMvtStk(LigneVente lig) {
        MvtStockDto mvtStkDto = MvtStockDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvtStock(TypeMvtStock.SORTIE)
                .sourceMvt(SourceMvtStock.VENTE)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}
