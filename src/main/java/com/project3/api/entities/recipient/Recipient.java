package com.project3.api.entities.recipient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project3.api.entities.message.Message;
import com.project3.api.entities.user.User;
import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity(name = "recipient_info")
public class Recipient {
    @Id
    @SequenceGenerator(
            name = "recipient_sequence",
            sequenceName = "recipient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "recipient_sequence"
    )
    @Column(
            name = "rid",
            updatable = false
    )
    private Long rid;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName="mid", updatable = false)
    private Message message;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "recipient", referencedColumnName="id", updatable = false)
    private User user;

    public Recipient() {
    }

    public Recipient(Long rid, Message message, User user) {
        this.rid = rid;
        this.message = message;
        this.user = user;
    }

    public Recipient(Message message, User user) {
        this.message = message;
        this.user = user;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) { this.message = message; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "rid=" + rid +
                ", message=" + message.toString() +
                ", user=" + user.toString() +
                '}';
    }
}
