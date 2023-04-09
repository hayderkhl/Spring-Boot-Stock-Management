package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.UtilisateurApi;
import com.haidar.gestiondestock.dto.ChangeMotDePasseDto;
import com.haidar.gestiondestock.dto.UtilisateurDto;
import com.haidar.gestiondestock.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurController implements UtilisateurApi {

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    UtilisateurService utilisateurService;

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return utilisateurService.save(dto);
    }

    @Override
    public UtilisateurDto findById(Integer idfutilisateur) {
        return utilisateurService.findById(idfutilisateur);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    @Override
    public void delete(Integer idfutilisateur) {
       utilisateurService.delete(idfutilisateur);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangeMotDePasseDto dto) {
        return utilisateurService.changeMotDePasse(dto);
    }
}
