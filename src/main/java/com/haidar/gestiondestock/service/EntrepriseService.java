package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.EntrepriseDto;
import com.haidar.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer id);

    List<EntrepriseDto> findAll();

    void delete(Integer id);
}
