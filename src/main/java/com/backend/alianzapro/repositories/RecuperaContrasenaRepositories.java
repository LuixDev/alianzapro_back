package com.backend.alianzapro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.alianzapro.models.RecuperaContrasena;

@Repository
public interface RecuperaContrasenaRepositories extends JpaRepository<RecuperaContrasena, Long> {
    boolean existsByEmail(String email);
    RecuperaContrasena findByEmail(String email);
}
