package com.haidar.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haidar.gestiondestock.model.CommandeClient;
import com.haidar.gestiondestock.model.CommandeFournisseur;
import com.haidar.gestiondestock.model.EtatCommand;
import com.haidar.gestiondestock.model.Fournisseur;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeFournisseurDto {

    private Integer id;
    private String code;
    private Instant dateCommande;
    private Fournisseur fournisseur;
    private EtatCommand etatCommand;
    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur) {
        if (commandeFournisseur == null) {
            return null;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .etatCommand(commandeFournisseur.getEtatCommande())
                .dateCommande(commandeFournisseur.getDateCommande())
                .fournisseur(commandeFournisseur.getFournisseur())
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto) {
        if (commandeFournisseurDto == null) {
            return null;
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommand());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setFournisseur(commandeFournisseurDto.getFournisseur());
        return commandeFournisseur;
    }
    public boolean isCommandeLivree() {
        return EtatCommand.LIVREE.equals(this.etatCommand);
    }
}
