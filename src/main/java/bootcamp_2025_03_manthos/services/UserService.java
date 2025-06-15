package bootcamp_2025_03_manthos.services;

import bootcamp_2025_03_manthos.exceptions.BootcampException;
import bootcamp_2025_03_manthos.model.User;
import bootcamp_2025_03_manthos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<User> getUsers() {
        return userRepository.findAll(); // Fetch users from the database
    }

    public User getUserById(long id) throws BootcampException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        }
        throw new BootcampException(HttpStatus.NOT_FOUND, "User not found");

    }

    public User getUserByEmail(String email) throws BootcampException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        }
        throw new BootcampException(HttpStatus.NOT_FOUND, "User not found");
    }

    public User create(User user) throws BootcampException {
        if (user.getId() != null) {
            throw new BootcampException(HttpStatus.BAD_REQUEST, "User id must be null");
        }
        if (user.getName() == null || user.getName().isEmpty()){
            throw new BootcampException(HttpStatus.BAD_REQUEST, "User name is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()){
            throw new BootcampException(HttpStatus.BAD_REQUEST, "User email is required");
        }

        user = userRepository.save(user);
        return user;
    }

    public User updateById(Long userIdToUpdate, User newUser) throws BootcampException {

        User existingUser = getUserById(userIdToUpdate);

        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPassword(newUser.getPassword());

        existingUser = userRepository.save(existingUser);

        return existingUser;

    }

    public User deleteById(Long id) throws BootcampException {
        User deletedUser = getUserById(id);
        userRepository.deleteById(id);

        return deletedUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Authenticating: " + username);
        User user = null;
        try {
            user = this.getUserByEmail(username);
            System.out.println("User password from DB: '" + user.getPassword() + "'");
            return user;
        } catch (BootcampException e) {
            throw new UsernameNotFoundException("User not found with username: " + username, e);
        }

    }
}
