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
@Table(name ="commandeClient")
public class CommandeClient extends AbstractEntity {

    @Column(name = "code")
    private String code;
    @Column(name = "dateCommande")
    private Instant dateCommande;
    @Column(name = "identreprise")
    private Integer idEntreprise;
    @Column(name = "etatcommande")
    private EtatCommand etatCommande;
    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;
    @OneToMany(mappedBy = "commandeClient")
    private List<LigneCommandeClient> ligneCommandeClients;
}
