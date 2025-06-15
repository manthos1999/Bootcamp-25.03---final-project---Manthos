package bootcamp_2025_03_manthos.repository;

import bootcamp_2025_03_manthos.model.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatThreadRepository extends JpaRepository<ChatThread, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM threads WHERE name ?1")
    Optional<java.lang.Thread> findByNameWithNativeQuery(String name);

}
