package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.FournisseurDto;
import java.util.List;

public interface FournisseurService {

    FournisseurDto save(FournisseurDto dto);

    FournisseurDto findById(Integer id);

    List<FournisseurDto> findAll();

    void delete(Integer id);
}
