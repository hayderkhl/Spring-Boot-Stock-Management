package com.haidar.gestiondestock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.ArticleDto;
import com.haidar.gestiondestock.model.Article;
import com.haidar.gestiondestock.service.ArticleService;
import com.haidar.gestiondestock.service.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {
    private FlickrService flickrService;
    private ArticleService articleService;

    @Autowired
    public SaveArticlePhoto(ArticleService articleService, FlickrService flickrService) {
        this.articleService = articleService;
        this.flickrService = flickrService;
    }

    public SaveArticlePhoto() {
    }

    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {

        ArticleDto article = articleService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo,titre);
        if(!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("error lors de l'encastrement de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        article.setPhoto(urlPhoto);
        return articleService.save(article);
    }
}
