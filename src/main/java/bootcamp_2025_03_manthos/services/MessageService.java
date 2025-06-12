package bootcamp_2025_03_manthos.services;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.repository.ChatThreadRepository;
import bootcamp_2025_03_manthos.repository.MessagesRepository;
import bootcamp_2025_03_manthos.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import bootcamp_2025_03_manthos.model.dto.ChatMessage;
import bootcamp_2025_03_manthos.model.dto.ChatCompletionRequest;
import bootcamp_2025_03_manthos.model.dto.ChatCompletionResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

private String groqApiKey = "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";

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

        // create a RestTemplate instance to make HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // creating authorized Http headers using groq api key
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + groqApiKey);

        // creating JSON Body that matches groq api key
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(newMessage.getCompletionModel());


        ChatMessage systemMessage = new ChatMessage("system", "You are a helpfull assistant");
        ChatMessage userMessage = new ChatMessage("user", newMessage.getContent());

        chatCompletionRequest.setMessages(List.of(systemMessage, userMessage));

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
        responseMessage.setChatThread(newMessage.getChatThread());

        return responseMessage;


    }

}