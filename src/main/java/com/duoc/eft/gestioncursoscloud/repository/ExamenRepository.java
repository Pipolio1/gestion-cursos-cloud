package com.duoc.eft.gestioncursoscloud.repository;

import com.duoc.eft.gestioncursoscloud.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findByCursoId(Long cursoId);
}
