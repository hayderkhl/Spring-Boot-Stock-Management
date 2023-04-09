package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.MvtStockDto;

import java.math.BigDecimal;
import java.util.List;

public interface MvtStkService {

    BigDecimal stockRealArticle(Integer idArticle);
    List<MvtStockDto> mvtStkArticle(Integer idArticle);
    MvtStockDto entreeStock(MvtStockDto dto);
    MvtStockDto sortieStock(MvtStockDto dto);
    MvtStockDto correctionStockPositive(MvtStockDto dto);
    MvtStockDto correctionStockNegative(MvtStockDto dto);

}
