package bootcamp_2025_03_manthos.controllers;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.model.ChatThread;
import bootcamp_2025_03_manthos.services.ChatThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatThreads")
public class ChatThreadController {

    @Autowired
    private ChatThreadService chatThreadService;
    private JwtEncoder jwtEncoder;

    @Autowired
    public ChatThreadController(ChatThreadService chatThreadService, JwtEncoder jwtEncoder) {
        this.chatThreadService = chatThreadService;
        this.jwtEncoder = jwtEncoder;
    }

    @GetMapping("")
    public List<ChatThread> getChatThreads() throws BootcampException {
        return chatThreadService.getChatThreads();
    }

    @GetMapping(value = "/{id}")
    public ChatThread getChatThreadById(@PathVariable long id) throws BootcampException {
        return chatThreadService.getChatThreadById(id);
    }

    @PostMapping("")
    public ChatThread createChatThread(@RequestBody ChatThread chatThread) throws BootcampException {
        return chatThreadService.createChatThread(chatThread);
    }

    @PutMapping("/{id}")
    public ChatThread updateChatThreadById(@PathVariable long id, @RequestBody ChatThread chatThread) throws BootcampException {
        ChatThread updatedChatThread = chatThreadService.updateChatThreadById(id, chatThread);

        return updatedChatThread;

    }

    @DeleteMapping("/{id}")
    public ChatThread deleteChatThreadById(@PathVariable long id) throws BootcampException {
        ChatThread deletedChatThread = chatThreadService.deleteById(id);

        return deletedChatThread;
    }


}
