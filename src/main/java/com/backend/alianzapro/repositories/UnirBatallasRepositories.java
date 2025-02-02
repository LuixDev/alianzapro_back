

package com.backend.alianzapro.repositories;

import com.backend.alianzapro.models.UnirBatallas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UnirBatallasRepositories extends JpaRepository<UnirBatallas, Long> {
    // Puedes agregar métodos personalizados si es necesario
    // Ejemplo: Buscar por agencia
    List<UnirBatallas> findByAgencia(String agencia);
    UnirBatallas findByActualIdAndHostId(int actualId, int hostId);

}
