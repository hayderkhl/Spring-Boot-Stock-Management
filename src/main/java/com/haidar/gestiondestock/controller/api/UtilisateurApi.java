package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/utilisateurs")
public interface UtilisateurApi {

    @PostMapping(value = APP_ROOT + "/utilisateurs/create")
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(value = APP_ROOT + "/utilisateurs/{idfutilisateur}")
    UtilisateurDto findById(@PathVariable Integer idfutilisateur);

    @GetMapping(value = APP_ROOT + "/utilisateurs/all")
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/utilisateurs/delete/{idfutilisateur}")
    void delete(@PathVariable Integer idfutilisateur);
}
