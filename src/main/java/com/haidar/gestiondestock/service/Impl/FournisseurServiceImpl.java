package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.FournisseurDto;
import com.haidar.gestiondestock.model.CommandeClient;
import com.haidar.gestiondestock.model.CommandeFournisseur;
import com.haidar.gestiondestock.model.Fournisseur;
import com.haidar.gestiondestock.repository.CommandeFournisseurRepository;
import com.haidar.gestiondestock.repository.FournisseurRepository;
import com.haidar.gestiondestock.service.FournisseurService;
import com.haidar.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;
    @Autowired
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validator(dto);
        if(!errors.isEmpty()) {
            log.error("Fournisseur is not validate {}", dto);

            throw new InvalidEntityException("L'Fournisseur n'est pas valide", ErrorCodes.ARTICLE_NOT_valid);
        }
        return FournisseurDto.fromEntity(fournisseurRepository.save(
                FournisseurDto.toEntity(dto)
        ));
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if(id == null) {
            log.error("Fournisseur is null");
            return null;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return Optional.of(FournisseurDto.fromEntity(fournisseur.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Fournisseur with this ID"+ id + " found in DB"
                        , ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Fournisseur is null");
            return;
        }

        List<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseur.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }

        fournisseurRepository.deleteById(id);
    }
}
