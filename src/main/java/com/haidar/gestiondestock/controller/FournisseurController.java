package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.FournisseurApi;
import com.haidar.gestiondestock.dto.FournisseurDto;
import com.haidar.gestiondestock.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FournisseurController implements FournisseurApi {

    @Autowired
    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    FournisseurService fournisseurService;

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        return fournisseurService.save(dto);
    }

    @Override
    public FournisseurDto findById(Integer idfournisseur) {
        return fournisseurService.findById(idfournisseur);
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    @Override
    public void delete(Integer idfournisseur) {
        fournisseurService.delete(idfournisseur);
    }
}
