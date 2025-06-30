package bootcamp_2025_03_manthos.services;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.model.ChatThread;
import bootcamp_2025_03_manthos.repository.ChatThreadRepository;
import bootcamp_2025_03_manthos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

@Service
public class ChatThreadService {

    private final UserRepository userRepository;
    private ChatThreadRepository chatThreadRepository;

    @Autowired
    public ChatThreadService(ChatThreadRepository chatThreadRepository, UserRepository userRepository) {
        this.chatThreadRepository = chatThreadRepository;
        this.userRepository = userRepository;
    }

    public List<ChatThread> getChatThreads() {
        return chatThreadRepository.findAll();
    }

    public ChatThread getChatThreadById(long id) throws BootcampException {
        Optional<ChatThread> chatThread = chatThreadRepository.findById(id);

        if (chatThread.isPresent()) {
            return chatThread.get();
        } else {
            throw new BootcampException("Thread not found");
        }
    }

    public ChatThread createThread(ChatThread chatThread) throws BootcampException {
        if (chatThread.getId() != null) {
            throw new BootcampException("Chat thread must be null");
        }
        if (chatThread.getName() == null || chatThread.getName().isEmpty()) {
            throw new BootcampException(("Chat thread name cannot be empty"));
        }
        if (chatThread.getUser() == null) {
            throw new BootcampException(("Chat thread user_id cannot be empty"));
        }

        chatThread.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return chatThreadRepository.save(chatThread);
    }

    public List<ChatThread> getThreadsByUserId(Long userId) throws BootcampException {
        return chatThreadRepository.findByUserId(userId);
    }

    public ChatThread updateThreadById(Long chatIdToUpdate, ChatThread newChatThread) throws BootcampException {

        ChatThread existingChatThread = getChatThreadById(chatIdToUpdate);

        existingChatThread.setName(newChatThread.getName());
        existingChatThread.setUser(newChatThread.getUser());

        existingChatThread = chatThreadRepository.save(existingChatThread);

        return existingChatThread;
    }

    public ChatThread deleteById(long id) throws BootcampException {
        ChatThread deletedChatThread = getChatThreadById(id);
        chatThreadRepository.deleteById(id);

        return deletedChatThread;
    }



}
