package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.VentesDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "ventes")
public interface VenteApi {

    @PostMapping(value = APP_ROOT + "/ventes/create")
    VentesDto save(@RequestBody VentesDto dto);

    @GetMapping(value = APP_ROOT + "/ventes/{idvente}")
    VentesDto findById(@PathVariable Integer idvente);
    @GetMapping(value = APP_ROOT + "/ventes/{codevente}")
    VentesDto findVenteByCode(@PathVariable String codevente);

    @GetMapping(value = APP_ROOT + "/ventes/all")
    List<VentesDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/ventes/delete/{idvente}")
    void delete(@PathVariable Integer idvente);
}
