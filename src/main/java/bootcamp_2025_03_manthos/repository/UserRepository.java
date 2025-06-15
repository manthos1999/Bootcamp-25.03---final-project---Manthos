package bootcamp_2025_03_manthos.repository;

import bootcamp_2025_03_manthos.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.email = ?1")
    @EntityGraph(attributePaths = {"threads"})
    Optional<User> findByEmail(String email);




}
