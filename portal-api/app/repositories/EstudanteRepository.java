package repositories;

import models.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstudanteRepository extends JpaRepository<Estudante, Integer> {

    @Query("select e from Estudante e join e.usuario u where u.login = ?1")
    Estudante findByLogin(String login);

    Estudante findByCpf(String cpf);

    @Query("select e from Estudante e join e.usuario u where u.email = ?1")
    Estudante findByEmail(String email);

    @Query("select e from Estudante e where e.usuario.login = :login and e.usuario.senha = :senha")
    Estudante findByLoginAndSenha(@Param("login") String login, @Param("senha") String senha);

}
