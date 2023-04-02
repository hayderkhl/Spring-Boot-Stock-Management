package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.CommandeFournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandesfournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(value = APP_ROOT + "/commandesfournisseurs/create")
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @GetMapping(value = APP_ROOT + "/commandesfournisseurs/{idcommandefournisseur}")
    CommandeFournisseurDto findById(@PathVariable Integer idcommandefournisseur);
    @GetMapping(value = APP_ROOT + "/commandesfournisseurs/{codecommandefournisseur}")
    CommandeFournisseurDto findCommandeFournisseurByCode(@PathVariable String codecommandefournisseur);

    @GetMapping(value = APP_ROOT + "/commandesfournisseurs/all")
    List<CommandeFournisseurDto> findAll();


    @DeleteMapping(value = APP_ROOT + "/commandesfournisseurs/delete/{idcommandefournisseur}")
    void delete(@PathVariable Integer idcommandefournisseur);
}
