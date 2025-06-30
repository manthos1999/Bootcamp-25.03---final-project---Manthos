package bootcamp_2025_03_manthos.services;

import bootcamp_2025_03_manthos.model.ChatThread;
import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.repository.ChatThreadRepository;
import bootcamp_2025_03_manthos.repository.MessagesRepository;
import bootcamp_2025_03_manthos.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import bootcamp_2025_03_manthos.model.dto.ChatMessage;
import bootcamp_2025_03_manthos.model.dto.ChatCompletionRequest;
import bootcamp_2025_03_manthos.model.dto.ChatCompletionResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Autowired
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

    public Message createMessageAndGetCompletion(Message newMessage) {

        newMessage.setLLMGenerated(false);
        newMessage.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        ChatThread thread = chatThreadRepository.findById(newMessage.getThread().getId())
                .orElseThrow(() -> new RuntimeException("Thread not found"));

        newMessage.setThread(thread);
        messagesRepository.save(newMessage);
        List<Message> threadMessages = messagesRepository.findByThreadOrderByCreatedAtAsc(thread);

        List<ChatMessage> chatMessages = new ArrayList<>();

        chatMessages.add(new ChatMessage("system", "You are a helpfull assistant"));

        for (Message msg : threadMessages) {
            String role = msg.isLLMGenerated() ? "assistant" : "user";
            chatMessages.add(new ChatMessage(role, msg.getContent()));
        }

        // create a RestTemplate instance to make HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // creating authorized Http headers using groq api key
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + groqApiKey);

        // creating JSON Body that matches groq api key
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(newMessage.getCompletionModel());
        chatCompletionRequest.setMessages(chatMessages);


//        ChatMessage systemMessage = new ChatMessage("system", "You are a helpfull assistant");
//        ChatMessage userMessage = new ChatMessage("user", newMessage.getContent());
//
//        chatCompletionRequest.setMessages(List.of(systemMessage, userMessage));

        // final Http object with header and body
        HttpEntity<ChatCompletionRequest> httpEntity =
                new HttpEntity<>(chatCompletionRequest, headers);

        // sending a POST request to Groqs endpoint
        ChatCompletionResponse response = restTemplate.postForEntity(
                "https://api.groq.com/openai/v1/chat/completions",
                httpEntity,
                ChatCompletionResponse.class
        ).getBody();

        Message responseMessage = new Message();
        responseMessage.setLLMGenerated(true);
        responseMessage.setContent(response.getChoices().get(0).getMessage().getContent());
        responseMessage.setCompletionModel(response.getModel());
        responseMessage.setThread(newMessage.getThread());
        responseMessage.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        messagesRepository.save(responseMessage);

        return responseMessage;


    }

    public List<Message> getMessagesByThreadId(Long threadId) {
        ChatThread thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found"));
        return messagesRepository.findByThreadOrderByCreatedAtAsc(thread);
    }

}