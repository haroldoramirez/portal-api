package repositories;

import models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Query("select c from Cidade c join c.estado e where e.sigla = ?1")
    List<Cidade> findByUf(String uf);

}
