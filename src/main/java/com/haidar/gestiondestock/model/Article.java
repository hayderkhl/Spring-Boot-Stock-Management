package com.haidar.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(name ="articale")
public class Article extends AbstractEntity{

    @Column(name = "codearticle")
    private String codeArticle;
    @Column(name = "designation")
    private String designation;
    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaire;
    @Column(name = "tauxTva")
    private BigDecimal tauxTva;
    @Column(name = "prixUnitaireTTc")
    private BigDecimal prixUnitaireTTc;

    @Column(name = "identreprise")
    private Integer idEntreprise;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes = new ArrayList<>();
}
