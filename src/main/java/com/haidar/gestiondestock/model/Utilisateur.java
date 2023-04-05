package com.haidar.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
@Table(name ="utilisateur")
public class Utilisateur extends AbstractEntity {
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "email")
    private String email;
    @Column(name = "dateDeNaissance")
    private Instant dateDeNaissance;
    @Column(name = "motDePasse")
    private String motDePasse;
    @Embedded
    private Adresse adresse;
    @Column(name = "photo")
    private String photo;
    @ManyToOne
    @JoinColumn(name = "identreprise")
    private Entreprise entreprise;

    @OneToMany(mappedBy = "utilisateur")
    private List<Roles> roles;
}
