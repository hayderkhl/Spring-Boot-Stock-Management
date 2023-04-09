package com.haidar.gestiondestock.service.Impl;

import com.haidar.gestiondestock.Exception.EntityNotFoundException;
import com.haidar.gestiondestock.Exception.ErrorCodes;
import com.haidar.gestiondestock.Exception.InvalidEntityException;
import com.haidar.gestiondestock.Exception.InvalidOperationException;
import com.haidar.gestiondestock.dto.ChangeMotDePasseDto;
import com.haidar.gestiondestock.dto.UtilisateurDto;
import com.haidar.gestiondestock.model.Utilisateur;
import com.haidar.gestiondestock.repository.UtilisateurRepository;
import com.haidar.gestiondestock.service.UtilisateurService;
import com.haidar.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("Article is not Utilisateur {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        String encodedPassword = passwordEncoder.encode(dto.getMotDePasse());
        dto.setMotDePasse(encodedPassword);

        return UtilisateurDto.fromEntity(utilisateurRepository.save(
                UtilisateurDto.toEntity(dto)
        ));
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if(id == null) {
            log.error("Article is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Utilisateur with this ID"+ id + " found in DB"
                        , ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Utilisateur is null");
            return;
        }

        utilisateurRepository.deleteById(id);

    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun Utilisateur avec ce mail" + email + "n'est trouve",
                        ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    @Override
    public UtilisateurDto changeMotDePasse(ChangeMotDePasseDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if(utilisateurOptional.isEmpty()) {
            log.warn("aucun utilisateur de cette ID");
            throw new EntityNotFoundException("Utilisateur not found", ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(dto.getMotDePasse());
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }
    private void validate(ChangeMotDePasseDto dto) {
        if(dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet null");
            throw new InvalidOperationException("Aucune information est fournie pour changer le mot de passe",ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null) {
            log.warn("ID utilisateur not found");
            throw new InvalidOperationException("Aucune information est fournie pour changer le mot de passe",ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
            log.warn("Impossible de changer le mot de passe avec un mot de passe null");
            throw new InvalidOperationException("Aucune information est fournie pour changer le mot de passe",ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);

        }
        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
            log.warn("the passwords are different");
            throw new InvalidOperationException("Different password",ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

    }
}
