package bootcamp_2025_03_manthos.controllers;


import bootcamp_2025_03_manthos.model.User;
import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.model.TokenDTO;
import bootcamp_2025_03_manthos.repository.UserRepository;
import bootcamp_2025_03_manthos.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private JwtEncoder jwtEncoder;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;

    }

    @GetMapping("")
    public List<User> getUsers() {
        logger.debug("Get all users from UserController");
        return userService.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable long id) throws BootcampException {
        return userService.getUserById(id);
    }

//    @GetMapping(value = "", params = "id")
//    public User getUserByParamId(@RequestParam long id) throws BootcampException {
//        return userService.getUserById(id);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserWithThreads(@PathVariable Long id) {
//        Optional<User> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            return ResponseEntity.ok(userOptional.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) throws BootcampException {

        User createdUser = userService.create(user);
        return createdUser;

    }

    @PostMapping("/login")
    public TokenDTO login(Authentication authentication) throws BootcampException {

        User loggedInUser = ((User)authentication.getPrincipal());
        // 2) Build JWT claims
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(6, ChronoUnit.HOURS))
                .subject(loggedInUser.getUsername())
//                .claim("roles", auth.getAuthorities().stream()
//                        .map(a -> a.getAuthority()).toList())
                .build();

        // 3) Encode & return token
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return tokenDTO;
    }

    @PutMapping(value = "/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) throws BootcampException {
        if (user.getId() != null && user.getId() != id) {
            throw new BootcampException("User id mismatch");
        }

        User updatedUser = userService.updateById(id, user);
        return updatedUser;

    }

    @DeleteMapping(value = "/{id}")
    public User deleteUser(@PathVariable long id) throws BootcampException {
        User deletedUser = userService.deleteById(id);
        return deletedUser;
    }



}
