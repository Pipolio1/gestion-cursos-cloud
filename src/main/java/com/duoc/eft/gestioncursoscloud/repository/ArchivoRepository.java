package com.duoc.eft.gestioncursoscloud.repository;

import com.duoc.eft.gestioncursoscloud.model.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    List<Archivo> findByCursoId(Long cursoId);
}
