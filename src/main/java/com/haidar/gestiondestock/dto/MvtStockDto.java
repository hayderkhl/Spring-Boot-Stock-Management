package com.haidar.gestiondestock.dto;

import com.haidar.gestiondestock.model.MvtStock;
import com.haidar.gestiondestock.model.SourceMvtStock;
import com.haidar.gestiondestock.model.TypeMvtStock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
@Builder
@Data
public class MvtStockDto {

    private Integer id;
    private ArticleDto article;
    private Instant dateMvt;
    private BigDecimal quantite;
    private TypeMvtStock typeMvtStock;
    private SourceMvtStock sourceMvt;
    private Integer idEntreprise;

    public static MvtStockDto fromEntity(MvtStock mvtStock) {
        if (mvtStock == null) {
            return null;
        }

       return MvtStockDto.builder()
                .id(mvtStock.getId())
                .dateMvt(mvtStock.getDateMvt())
                .quantite(mvtStock.getQuantite())
                .typeMvtStock(mvtStock.getTypeMvtStock())
               .sourceMvt(mvtStock.getSourceMvt())
               .idEntreprise(mvtStock.getIdEntreprise())
                .build();
    }

    public static MvtStock toEntity(MvtStockDto mvtStockDto) {
        if (mvtStockDto == null) {
            return null;
        }
        MvtStock mvtStock = new MvtStock();
        mvtStock.setId(mvtStockDto.getId());
        mvtStock.setDateMvt(mvtStock.getDateMvt());
        mvtStock.setQuantite(mvtStockDto.getQuantite());
        mvtStock.setSourceMvt(mvtStockDto.getSourceMvt());
        mvtStock.setTypeMvtStock(mvtStockDto.getTypeMvtStock());
        mvtStock.setIdEntreprise(mvtStockDto.getIdEntreprise());
        return mvtStock;
    }

}
