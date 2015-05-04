package repositories;

import models.CursosEstudante;
import models.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursosEstudanteRepository extends JpaRepository<CursosEstudante, Integer> {

    @Query("select c from CursosEstudante c join c.estudante e where e.nomeCompleto = ?1")
    CursosEstudante findByNome(String nome);

    @Query("select c from CursosEstudante c join c.estudante e where e.id = ?1")
    List<CursosEstudante> findCursosEstudanteByIdEstudante(Integer id);

}
