package com.project3.api.entities.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.project3.api.functions.Hash.getSHA;
import static com.project3.api.functions.Hash.toHexString;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user;
        String usrname = "";
        String password = "";
        Optional<User> login = userRepository.findUserByUsername(username);

        if (login.isPresent()) {
            user = login.get();
            usrname = user.getUsername();
            password = user.getPassword();

            return new org.springframework.security.core.userdetails.User(usrname, password, new ArrayList<>());
        }

        throw new UsernameNotFoundException("Username does not exist");
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
            if (userByIdAndUsername.isEmpty()) {
                throw new IllegalStateException("User with id " + userId + " and username " + username + " not found!");
            }
        }
        if (userId != null) {
            Optional<User> userById = userRepository.findUserById(userId);
            if (userById.isEmpty()) {
                throw new IllegalStateException("User with id " + userId + " does not exist!");
            }
            return userById.get();
        }
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername.isEmpty()) {
            throw new IllegalStateException("User with username " + username + " does not exist!");
        }
        return userByUsername.get();
    }

    public void addNewUser(User user, MultipartFile file) {
        System.out.println(user);
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent()) {
            throw new IllegalStateException("Username already taken!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegisteredAt(Timestamp.valueOf(LocalDateTime.now()));
        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File("/static/images/" + fileName));
            } catch (IOException e) {
                throw new IllegalStateException("Error uploading avatar");
            }
            user.setAvatarUrl("/static/images/" + fileName);
        } else {
            user.setAvatarUrl("/static/images/default.png");
        }
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
    public void updateUser(Long userId, String username, String email, String password, MultipartFile file) {
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

            if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(password));
            }
        }

        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File("/static/images/" + fileName));
            } catch (IOException e) {
                throw new IllegalStateException("Error uploading avatar");
            }
            user.setAvatarUrl("/static/images/" + fileName);
        }
    }
}