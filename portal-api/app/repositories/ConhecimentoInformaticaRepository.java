package repositories;

import models.ConhecimentoInformatica;
import models.GrupoConhecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ConhecimentoInformaticaRepository extends JpaRepository<ConhecimentoInformatica, Integer> {

    @Query("select ci from ConhecimentoInformatica ci join ci.grupoConhecimento gc where gc.id = ?1")
    List<ConhecimentoInformatica> findByIdGrupo(Integer idGrupo);
}
