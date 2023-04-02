package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.CommandeFournisseurApi;
import com.haidar.gestiondestock.dto.CommandeFournisseurDto;
import com.haidar.gestiondestock.service.CommandeFournisseurService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    CommandeFournisseurService commandeFournisseurService;
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        return commandeFournisseurService.save(dto);
    }

    @Override
    public CommandeFournisseurDto findById(Integer idcommandefournisseur) {
        return commandeFournisseurService.findById(idcommandefournisseur);
    }

    @Override
    public CommandeFournisseurDto findCommandeFournisseurByCode(String codecommandefournisseur) {
        return commandeFournisseurService.findCommandeFournisseurByCode(codecommandefournisseur);
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
       return commandeFournisseurService.findAll();
    }

    @Override
    public void delete(Integer idcommandefournisseur) {
         commandeFournisseurService.delete(idcommandefournisseur);
    }
}
