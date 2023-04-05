package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/categories")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une category (Ajouter/Modifier)", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "L'objet category cree/modifier"),
            @ApiResponse(code =400, message = "champs manquants")
    })
    CategoryDto save(@RequestBody CategoryDto dto);

    @GetMapping(value = APP_ROOT + "/article/{idcategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un article ", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "L'objet category est trouvé dans le DB"),
            @ApiResponse(code =404, message = "L'objet category n'est pas trouver dans le DB")
    })
    CategoryDto findById(@PathVariable("idcategory") Integer id);

    @GetMapping(value = APP_ROOT + "/category/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher les articles ", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "Les categories sont trouvés dans le DB / une List vide")})
    List<CategoryDto> findAll();

    @DeleteMapping(APP_ROOT + "/categories/delete/{idCategoy}")
    void delete(@PathVariable("idCategoy") Integer id);
}
