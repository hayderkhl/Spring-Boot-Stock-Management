package com.haidar.gestiondestock.repository;

import com.haidar.gestiondestock.model.MvtStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvStockRepository extends JpaRepository<MvtStock, Integer> {
}
