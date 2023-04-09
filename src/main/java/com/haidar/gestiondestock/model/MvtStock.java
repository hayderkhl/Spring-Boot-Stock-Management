package com.haidar.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="mvtStock")
public class MvtStock extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;
    @Column(name = "dateMvt")
    private Instant dateMvt;
    @Column(name = "quantite")
    private BigDecimal quantite;
    @Column(name = "sourcemvt")
    @Enumerated(EnumType.STRING)
    private SourceMvtStock sourceMvt;
    @Column(name = "typeMvtStock")
    private TypeMvtStock typeMvtStock;
    @Column(name = "identreprise")
    private Integer idEntreprise;
}
