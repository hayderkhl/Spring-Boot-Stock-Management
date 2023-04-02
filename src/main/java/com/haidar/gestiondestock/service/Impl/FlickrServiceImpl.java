package com.haidar.gestiondestock.service.Impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.haidar.gestiondestock.service.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
@Service
@Slf4j
@PropertySource("classpath:application.properties")
public class FlickrServiceImpl implements FlickrService {

    @Value("${flickr.api.key}")
    private String apiKey;
    @Value("${flickr.api.secret}")
    private String apiSecret;
    @Value("${flickr.app.key}")
    private String appkey;
    @Value("${flickr.app.secret}")
    private String appsecret;
    private Flickr flickr;
    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();

    }

    private void connect() {
        flickr = new Flickr(apiKey,apiSecret, new REST());

        Auth auth = new Auth();
        auth.setPermission(Permission.DELETE);

        auth.setToken(appkey);
        auth.setTokenSecret(appsecret);

        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);

        flickr.setAuth(auth);
    }
}
