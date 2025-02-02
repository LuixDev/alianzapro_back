
package com.backend.alianzapro.repositories;

import com.backend.alianzapro.models.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularioRepositories extends JpaRepository<Formulario, Long> {
    // Spring Data JPA automáticamente implementa los métodos CRUD (Create, Read, Update, Delete)
}
