package com.backend.alianzapro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.alianzapro.models.Batallas;

@Repository
public interface BatallasRepositories extends JpaRepository<Batallas, Long> {
    List<Batallas> findByTipoDeBatallaIgnoreCase(String tipoDeBatalla);
}


    
