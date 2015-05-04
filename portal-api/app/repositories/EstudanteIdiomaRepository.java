package repositories;

import models.EstudanteIdioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstudanteIdiomaRepository extends JpaRepository<EstudanteIdioma, Integer> {

    @Query("select ei from EstudanteIdioma ei join ei.estudante e where e.nomeCompleto = ?1")
    EstudanteIdioma findByNome(String nome);

    @Query("select ei from EstudanteIdioma ei join ei.estudante e where e.id = ?1")
    List<EstudanteIdioma> findByIdEstudante(Integer idEstudante);
}
