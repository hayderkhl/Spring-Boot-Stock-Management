package com.haidar.gestiondestock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.haidar.gestiondestock.dto.ArticleDto;

import java.io.InputStream;

public interface Strategy <T> {

    T savePhoto(Integer id, InputStream photo, String titre) throws FlickrException;
}
