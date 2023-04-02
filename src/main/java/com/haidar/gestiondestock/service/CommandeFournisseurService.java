package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.CommandeClientDto;
import com.haidar.gestiondestock.dto.CommandeFournisseurDto;

import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto findById(Integer id);
    CommandeFournisseurDto findCommandeFournisseurByCode(String code);

    List<CommandeFournisseurDto> findAll();

    void delete(Integer id);
}
