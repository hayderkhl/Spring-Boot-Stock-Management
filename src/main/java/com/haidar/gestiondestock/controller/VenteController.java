package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.VenteApi;
import com.haidar.gestiondestock.dto.VentesDto;
import com.haidar.gestiondestock.service.VenteService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VenteController implements VenteApi {

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    VenteService venteService;


    @Override
    public VentesDto save(VentesDto dto) {
        return venteService.save(dto);
    }

    @Override
    public VentesDto findById(Integer idvente) {
        return venteService.findById(idvente);
    }

    @Override
    public VentesDto findVenteByCode(String codevente) {
        return venteService.findVenteByCode(codevente);
    }

    @Override
    public List<VentesDto> findAll() {
        return venteService.findAll();
    }

    @Override
    public void delete(Integer idvente) {
        venteService.delete(idvente);
    }
}
