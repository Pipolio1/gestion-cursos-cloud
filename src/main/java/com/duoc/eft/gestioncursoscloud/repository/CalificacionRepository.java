package com.duoc.eft.gestioncursoscloud.repository;

import com.duoc.eft.gestioncursoscloud.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByExamenId(Long examenId);
    List<Calificacion> findByEstudianteId(Long estudianteId);
    Optional<Calificacion> findByExamenIdAndEstudianteId(Long examenId, Long estudianteId);
}
