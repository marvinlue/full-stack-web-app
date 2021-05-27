package com.project3.api.entities.user;

import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.member.Member;
import com.project3.api.entities.message.Message;
import com.project3.api.entities.post.Post;
import com.project3.api.entities.recipient.Recipient;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "users")
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username",
            columnDefinition = "TEXT"
    )
    private String username;

    @Column(
            name = "email",
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "registered_at",
            updatable = false
    )
    private Timestamp registeredAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Recipient> recipients = new ArrayList<>();

    public User() {
    }

    public User(Long id,
                String username,
                String email,
                String password,
                Timestamp registeredAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.registeredAt = registeredAt;
    }

    public User(String username,
                String email,
                String password,
                Timestamp registeredAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registeredAt = registeredAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registeredAt='" + registeredAt + '\'' +
                '}';
    }
}
