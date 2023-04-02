package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.dto.ClientDto;
import com.haidar.gestiondestock.dto.UtilisateurDto;
import com.haidar.gestiondestock.model.Client;
import com.haidar.gestiondestock.model.Utilisateur;
import com.haidar.gestiondestock.repository.ClientRepository;
import com.haidar.gestiondestock.repository.UtilisateurRepository;
import com.haidar.gestiondestock.service.ClientService;
import com.haidar.gestiondestock.validator.ClientValidator;
import com.haidar.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validator(dto);
        if(!errors.isEmpty()) {
            log.error("Client is not Utilisateur {}", dto);
            throw new InvalidEntityException("L'Client n'est pas valide", ErrorCodes.ARTICLE_NOT_valid);
        }
        return ClientDto.fromEntity(clientRepository.save(
                ClientDto.toEntity(dto)
        ));
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id == null) {
            log.error("Client is null");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);
        return Optional.of(ClientDto.fromEntity(client.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Client with this ID"+ id + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Client is null");
            return;
        }

        clientRepository.deleteById(id);

    }
}
