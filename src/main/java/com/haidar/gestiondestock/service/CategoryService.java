package com.haidar.gestiondestock.service;

import com.haidar.gestiondestock.dto.CategoryDto;
import com.haidar.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);
    CategoryDto findByCode(String code);

    List<CategoryDto> findAll();

    void delete(Integer id);
}
