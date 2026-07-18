package com.duoc.eft.gestioncursoscloud.repository;

import com.duoc.eft.gestioncursoscloud.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByEmail(String email);
}
