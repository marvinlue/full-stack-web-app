package com.project3.api.entities.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.project3.api.functions.Hash.getSHA;
import static com.project3.api.functions.Hash.toHexString;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByIdOrUsername(Long userId, String username) {
        if (userId == null && username == null) {
            throw new IllegalStateException("User id or username required for this route!");
        }
        if (userId != null && username != null) {
            Optional<User> userByIdAndUsername = userRepository.findByIdAndUsername(userId, username);
            if (!userByIdAndUsername.isPresent()) {
                throw new IllegalStateException("User with id " + userId + " and username " + username + " not found!");
            }
        }
        if (userId != null) {
            Optional<User> userById = userRepository.findUserById(userId);
            if (!userById.isPresent()) {
                throw new IllegalStateException("User with id " + userId + " does not exist!");
            }
            return userById.get();
        }
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (!userByUsername.isPresent()) {
            throw new IllegalStateException("User with username " + username + " does not exist!");
        }
        return userByUsername.get();
    }

    public void addNewUser(User user) {
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent()) {
            throw new IllegalStateException("Username already taken!");
        }
        try {
           user.setPassword(toHexString(getSHA(user.getPassword())));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
        user.setRegisteredAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist!");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String username, String email, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist!"));

        if (username != null &&
        username.length() > 0 &&
        !Objects.equals(user.getUsername(), username)) {
            Optional<User> userByUsername = userRepository.findUserByUsername(username);
            if (userByUsername.isPresent()) {
                throw new IllegalStateException("Username already taken!");
            }
            user.setUsername(username);
        }

        if (email != null &&
        email.length() > 0 &&
        !Objects.equals(user.getEmail(), email)) {
            user.setEmail(email);
        }

        if (password != null && password.length() > 0) {
            try {
                String hash = toHexString(getSHA(password));
                if (!Objects.equals(user.getPassword(), hash)) {
                    user.setPassword(hash);
                }
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Exception thrown for incorrect algorithm: " + e);
            }
        }
    }
}