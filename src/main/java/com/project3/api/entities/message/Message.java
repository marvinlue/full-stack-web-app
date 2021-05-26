package com.project3.api.entities.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project3.api.entities.recipient.Recipient;
import com.project3.api.entities.user.User;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity(name = "messages")
public class Message {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "message_sequence"
    )
    @Column(
            name = "mid",
            updatable = false
    )
    private Long mid;

    @Column(
            name = "message",
            columnDefinition = "TEXT"
    )
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName="id", updatable = false)
    private User user;

    @Column(
            name = "sent_at",
            updatable = false
    )
    private Timestamp sentAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "message", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Recipient> recipients = new ArrayList<>();

    public Message() {
    }

    public Message(Long mid, String message, User user, Timestamp sentAt) {
        this.mid = mid;
        this.message = message;
        this.user = user;
        this.sentAt = sentAt;
    }

    public Message(String message, User user, Timestamp sentAt) {
        this.message = message;
        this.user = user;
        this.sentAt = sentAt;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid=" + mid +
                ", message='" + message + '\'' +
                ", user=" + user.toString() +
                ", sentAt=" + sentAt +
                '}';
    }
}
