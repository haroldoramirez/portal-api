package repositories;

import models.EstudanteConhecimentoInformatica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstudanteConhecimentoInformaticaRepository extends JpaRepository<EstudanteConhecimentoInformatica, Integer> {

    @Query("select eci from EstudanteConhecimentoInformatica eci join eci.estudante e where e.nomeCompleto = ?1")
    EstudanteConhecimentoInformatica findByNome(String nome);

    @Query("select eci from EstudanteConhecimentoInformatica eci join eci.estudante e where e.id = ?1")
    List<EstudanteConhecimentoInformatica> findConhecimentoInformaticaByIdEstudante(Integer idEstudante);
}
