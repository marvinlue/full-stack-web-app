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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
        String path;
        System.out.println(user);
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent()) {
            throw new IllegalStateException("Username already taken!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegisteredAt(Timestamp.valueOf(LocalDateTime.now()));
        if (file != null) {
            path = "src/main/resources/static/images";
            File f = new File(path);
            String absolutePath = f.getAbsolutePath();
            set_avatar(user, file, absolutePath);
        } else {
            path = "src/main/resources/static/images";
            File f = new File(path);
            String absolutePath = f.getAbsolutePath();
            user.setAvatarUrl(absolutePath + "/" + "default.png");
        }
        userRepository.save(user);
    }

    private void set_avatar(User user, MultipartFile file, String absolutePath) {
        String fileName = file.getOriginalFilename();
        try {
            assert fileName != null;
            file.transferTo(new File(absolutePath, fileName));
        } catch (IOException e) {
            throw new IllegalStateException("Error uploading avatar");
        }
        user.setAvatarUrl(absolutePath + "/" + fileName);
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
            String path = "src/main/resources/static/images";
            File f = new File(path);
            String absolutePath = f.getAbsolutePath();
            set_avatar(user, file, absolutePath);
        }
    }
}