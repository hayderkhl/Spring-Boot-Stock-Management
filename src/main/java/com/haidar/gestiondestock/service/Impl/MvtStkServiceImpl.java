package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.dto.MvtStockDto;
import com.haidar.gestiondestock.model.TypeMvtStock;
import com.haidar.gestiondestock.repository.MvStockRepository;
import com.haidar.gestiondestock.service.ArticleService;
import com.haidar.gestiondestock.service.MvtStkService;
import com.haidar.gestiondestock.validator.MvtStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    MvStockRepository mvStockRepository;
    private ArticleService articleService;

    @Autowired
    public MvtStkServiceImpl(MvStockRepository mvStockRepository, ArticleService articleService) {
        this.mvStockRepository = mvStockRepository;
        this.articleService = articleService;
    }
    @Override
    public BigDecimal stockRealArticle(Integer idArticle) {
        if (idArticle == null) {
            log.warn("Id article null");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mvStockRepository.stockRealArticle(idArticle);
    }

    @Override
    public List<MvtStockDto> mvtStkArticle(Integer idArticle) {
        return mvStockRepository.findAllByArticleId(idArticle).stream()
                .map(MvtStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStockDto entreeStock(MvtStockDto dto) {
        List<String> errors = MvtStockValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Mouvement Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvtStock(TypeMvtStock.ENTREE);
        return MvtStockDto.fromEntity(
                mvStockRepository.save(MvtStockDto.toEntity(dto))
        );
    }

    @Override
    public MvtStockDto sortieStock(MvtStockDto dto) {
        return sortieNegative(dto, TypeMvtStock.SORTIE);
    }

    @Override
    public MvtStockDto correctionStockPositive(MvtStockDto dto) {
        List<String> errors = MvtStockValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvtStock(TypeMvtStock.CORRECTION_POSITIVE);
        return MvtStockDto.fromEntity(
                mvStockRepository.save(MvtStockDto.toEntity(dto))
        );
    }

    @Override
    public MvtStockDto correctionStockNegative(MvtStockDto dto) {
        return sortieNegative(dto, TypeMvtStock.CORRECTION_NEGATIVE);
    }

    private MvtStockDto sortieNegative(MvtStockDto dto, TypeMvtStock typeMvtStk) {
        List<String> errors = MvtStockValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvtStock(typeMvtStk.SORTIE);
        return MvtStockDto.fromEntity(
                mvStockRepository.save(MvtStockDto.toEntity(dto))
        );
    }
}
