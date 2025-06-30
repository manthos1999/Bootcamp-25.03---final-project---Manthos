package bootcamp_2025_03_manthos.repository;

import bootcamp_2025_03_manthos.model.ChatThread;
import bootcamp_2025_03_manthos.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface MessagesRepository extends JpaRepository<Message, Long> {

    @Query(nativeQuery = true,
        value = "SELECT * FROM messages WHERE name ?1")
    Optional<Message> findByNameWithNativeQuery(String name);

    List<Message> findByThreadOrderByCreatedAtAsc(ChatThread thread);
    List<Message> findByThreadId(Long threadId);

}
