package dev.anil.flightticketbookingsystem.repos;

import dev.anil.flightticketbookingsystem.models.UserModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(long id);

    boolean existsByEmail(String email);

    boolean existsUserByMobile(String mobile);

    Optional<User> findUserByEmail(String email);
}
