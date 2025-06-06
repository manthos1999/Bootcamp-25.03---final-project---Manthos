package bootcamp_2025_03_manthos.services;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.repository.ChatThreadRepository;
import bootcamp_2025_03_manthos.repository.MessagesRepository;
import bootcamp_2025_03_manthos.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessagesRepository messagesRepository;
    private ChatThreadRepository chatThreadRepository;

    @Autowired
    public MessageService(MessagesRepository messagesRepository, ChatThreadRepository chatThreadRepository) {
        this.messagesRepository = messagesRepository;
        this.chatThreadRepository = chatThreadRepository;
    }

    public List<Message> getMessages() {
        return messagesRepository.findAll();
    }

    public Message getMessageById(Long id) throws BootcampException {
        Optional<Message> message = messagesRepository.findById(id);

        if (message.isPresent()) {
            return message.get();
        }
        else {
            throw new BootcampException("Message not found");
        }
    }

}