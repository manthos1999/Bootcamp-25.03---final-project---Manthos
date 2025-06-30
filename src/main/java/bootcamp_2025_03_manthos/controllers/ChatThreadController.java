package bootcamp_2025_03_manthos.controllers;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.model.ChatThread;
import bootcamp_2025_03_manthos.services.UserService;
import bootcamp_2025_03_manthos.repository.ChatThreadRepository;
import bootcamp_2025_03_manthos.services.ChatThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/threads")
public class ChatThreadController {

    @Autowired
    private ChatThreadService chatThreadService;
    private ChatThreadRepository chatThreadRepository;
    private JwtEncoder jwtEncoder;
    private UserService userService;

    @Autowired
    public ChatThreadController(ChatThreadService chatThreadService,UserService userService, JwtEncoder jwtEncoder, ChatThreadRepository chatThreadRepository, AuthenticationManager authentication) {
        this.chatThreadService = chatThreadService;
        this.chatThreadRepository = chatThreadRepository;
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
    }

//    @GetMapping("")
//    public List<ChatThread> getThreads() throws BootcampException {
//        return chatThreadService.getChatThreads();
//    }

    @GetMapping(value = "/{id}")
    public ChatThread getThreadById(@PathVariable long id) throws BootcampException {
        return chatThreadService.getChatThreadById(id);
    }

    @GetMapping("/user")
    public List<ChatThread> getThreadsForLoggedInUser() throws BootcampException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Long userId = userService.findUserIdByEmail(email);

        List<ChatThread> threads = chatThreadService.getThreadsByUserId(userId);


        System.out.println("Fetched threads for user " + email + ": " +threads);

        return chatThreadService.getThreadsByUserId(userId);
    }


    @PostMapping("")
    public ChatThread createThread(@RequestBody ChatThread chatThread) throws BootcampException {
        return chatThreadService.createThread(chatThread);
    }

    @PutMapping("/{id}")
    public ChatThread updateThreadById(@PathVariable long id, @RequestBody ChatThread chatThread) throws BootcampException {
        ChatThread updatedChatThread = chatThreadService.updateThreadById(id, chatThread);

        return updatedChatThread;

    }

    @DeleteMapping("/{id}")
    public ChatThread deleteThreadById(@PathVariable long id) throws BootcampException {
        ChatThread deletedChatThread = chatThreadService.deleteById(id);

        return deletedChatThread;
    }


}
