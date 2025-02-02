package com.backend.alianzapro.repositories;

import com.backend.alianzapro.models.Login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepositories extends JpaRepository<Login, Long> {
    // Método para buscar un Login por nombre de usuario
    Login findByUsername(String username);

    // Método para buscar un Login por ID
    Optional findById(Long id);  // Este método ya no devuelve una lista, sino un único Login
}
