package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.MvmStockApi;
import com.haidar.gestiondestock.dto.MvtStockDto;
import com.haidar.gestiondestock.service.MvtStkService;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
@RestController
public class MvtStockController implements MvmStockApi {

    public MvtStockController(MvtStkService mvtStkService) {
        this.mvtStkService = mvtStkService;
    }

    private MvtStkService mvtStkService;
    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return mvtStkService.stockRealArticle(idArticle);
    }

    @Override
    public List<MvtStockDto> mvtStkArticle(Integer idArticle) {
        return mvtStkService.mvtStkArticle(idArticle);
    }

    @Override
    public MvtStockDto entreeStock(MvtStockDto dto) {
        return mvtStkService.entreeStock(dto);
    }

    @Override
    public MvtStockDto sortieStock(MvtStockDto dto) {
        return mvtStkService.sortieStock(dto);
    }

    @Override
    public MvtStockDto correctionStockPos(MvtStockDto dto) {
        return mvtStkService.correctionStockPositive(dto);
    }

    @Override
    public MvtStockDto correctionStockNeg(MvtStockDto dto) {
        return mvtStkService.correctionStockNegative(dto);
    }
}
