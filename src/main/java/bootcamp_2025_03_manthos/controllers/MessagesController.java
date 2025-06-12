package bootcamp_2025_03_manthos.controllers;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.services.MessageService;
import bootcamp_2025_03_manthos.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessagesController {

    @Autowired
    private MessageService messageService;
    private JwtEncoder jwtEncoder;

    @Autowired
    public MessagesController(MessageService messageService, JwtEncoder jwtEncoder) {
        this.messageService = messageService;
        this.jwtEncoder = jwtEncoder;
    }
/*
    @GetMapping("")
    public List<Message> getMessages() throws BootcampException {
        return messageService.getMessages();
    }*/

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable Long id) throws BootcampException {
        return messageService.getMessageById(id);
    }

    @PostMapping("")
    public Message createMessage(@RequestBody Message message) {


        //return response from LLM

        Message responseMessage = messageService.createMessageAndGetCompletion(message);


        return responseMessage;

    }



}
