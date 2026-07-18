package com.duoc.eft.gestioncursoscloud.repository;

import com.duoc.eft.gestioncursoscloud.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByInstructorEmail(String instructorEmail);
}
