package repositories;

import models.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BairroRepository extends JpaRepository<Bairro, Integer> {

    @Query("select b from Bairro b join b.cidade c where c.id = ?1")
    List<Bairro> findByCidade(Integer cidadeId);
}
