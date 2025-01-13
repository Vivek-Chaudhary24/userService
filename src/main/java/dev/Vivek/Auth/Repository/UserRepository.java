package dev.Vivek.Auth.Repository;

import dev.Vivek.Auth.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    @Override
    <S extends User> S save(S entity);
}
