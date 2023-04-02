package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/fournisseurs")
public interface FournisseurApi {

    @PostMapping(value = APP_ROOT + "/fournisseurs/create")
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(value = APP_ROOT + "/fournisseurs/{idfournisseur}")
    FournisseurDto findById(@PathVariable Integer idfournisseur);

    @GetMapping(value = APP_ROOT + "/fournisseurs/all")
    List<FournisseurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/fournisseurs/delete/{idfournisseur}")
    void delete(@PathVariable Integer idfournisseur);
}
