package repositories;

import models.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienciaRepository extends JpaRepository<Experiencia, Integer> {

    @Query("select ex from Experiencia ex join ex.estudante e where e.nomeCompleto = ?1")
    Experiencia findByNome(String nome);

    @Query("select ex from Experiencia ex join ex.estudante e where e.id = ?1")
    List<Experiencia> findExperienciasByIdEstudante(Integer id);
}
