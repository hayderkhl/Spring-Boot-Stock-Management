package com.haidar.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="commandeFournisseur")
public class CommandeFournisseur extends AbstractEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "dateCommande")
    private Instant dateCommande;
    @ManyToOne
    @JoinColumn(name = "idFournisseur")
    private Fournisseur fournisseur;
    @Column(name = "identreprise")
    private Integer idEntreprise;
    @Column(name = "etatcommande")
    private EtatCommand etatCommande;

    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}
