package com.haidar.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haidar.gestiondestock.model.Client;
import com.haidar.gestiondestock.model.CommandeClient;
import com.haidar.gestiondestock.model.EtatCommand;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeClientDto {

    private Integer id;
    private String code;
    private Instant dateCommande;
    private EtatCommand etatCommand;
    private ClientDto client;
    private Integer idEntreprise;
    private List<LigneCommandeClientDto> ligneCommandeClients;
    @JsonIgnore
    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {
        if (commandeClient == null) {
            return null;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .etatCommand(commandeClient.getEtatCommande())
                .idEntreprise(commandeClient.getIdEntreprise())
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto) {
        if (commandeClientDto == null) {
            return null;
        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommand());
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        return commandeClient;
    }

    public boolean isCommandeLivree() {
        return EtatCommand.LIVREE.equals(this.etatCommand);
    }

}
