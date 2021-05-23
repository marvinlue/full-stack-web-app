package com.example.project3.message;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(
            name = "sender",
            updatable = false
    )
    private Long sender;

    @Column(
            name = "sent_at",
            updatable = false
    )
    private Timestamp sentAt;

    public Message() {
    }

    public Message(Long mid, String message, Long sender, Timestamp sentAt) {
        this.mid = mid;
        this.message = message;
        this.sender = sender;
        this.sentAt = sentAt;
    }

    public Message(String message, Long sender, Timestamp sentAt) {
        this.message = message;
        this.sender = sender;
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

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
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
                ", sender=" + sender +
                ", sentAt=" + sentAt +
                '}';
    }
}
