package com.haidar.gestiondestock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {
    //we used the beanFactory because we have lot of services impliment the same interface
    //so to let spring boot know which one will use we added beanFactory
    @Autowired
    private BeanFactory beanFactory;
    private Strategy strategy;
    @Setter
    private String context;

    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String context, Integer id, InputStream photo, String title) throws FlickrException {
        determinContext(context);
        return strategy.savePhoto(id, photo, title);
    }
    private void determinContext(String context) {
        final String beanName = context + "Strategy";
        switch (context) {
            case "aarticle":
                strategy = beanFactory.getBean(beanName, SaveArticlePhoto.class);
                break;
            case "client":
                strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
                break;
            case "entreprise":
                strategy = beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
                break;
            case "fournisseur":
                strategy = beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
                break;
            case "utilisateur":
                strategy = beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
                break;
            default: throw new InvalidOperationException("Context inconnue", ErrorCodes.UNKNOWN_CONTEXT);

        }
    }

}
