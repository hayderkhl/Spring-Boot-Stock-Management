package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.ArticleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;
@Api(APP_ROOT+ "/articles")
public interface ArticleApi {

    @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un article (Ajouter/Modifier)", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "L'objet article cree/modifier"),
            @ApiResponse(code =400, message = "champs manquants")
    })
    ArticleDto save(@RequestBody ArticleDto dto);
    @GetMapping(value = APP_ROOT + "/article/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un article ", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "L'objet article est trouvé dans le DB"),
            @ApiResponse(code =404, message = "L'objet article n'est pas trouver dans le DB")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);
    @GetMapping(value =APP_ROOT + "/articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un article par CODE ", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "L'objet article est trouvé dans le DB"),
            @ApiResponse(code =404, message = "L'objet article n'est pas trouver dans le DB")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);
    @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher les articles ", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "Les articles sont trouvés dans le DB / une List vide")})
    List<ArticleDto> findAll();
    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @ApiOperation(value = "supprimer un article par ID ", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code =200, message = "L'objet article est supprimé de le DB")})
    void delete(@PathVariable("idArticle") Integer id);
}
