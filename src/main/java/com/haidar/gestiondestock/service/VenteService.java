package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.VentesDto;

import java.util.List;

public interface VenteService {

    VentesDto save(VentesDto dto);

    VentesDto findById(Integer id);
    VentesDto findVenteByCode(String code);

    List<VentesDto> findAll();

    void delete(Integer id);
}
