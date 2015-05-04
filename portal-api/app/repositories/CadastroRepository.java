package repositories;

import models.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CadastroRepository extends JpaRepository<Cadastro, Long> {

    @Query("select c from Cadastro c join c.usuario u where u.email = ?1")
    Cadastro findByEmail(String email);

    Cadastro findByDocumento(String documento);

}
