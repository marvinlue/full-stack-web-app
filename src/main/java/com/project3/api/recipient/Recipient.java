package com.project3.api.recipient;

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

    @Column(
            name = "message_id",
            updatable = false
    )
    private Long messageId;

    @Column(
            name = "recipient",
            updatable = false
    )
    private Long recipient;

    public Recipient() {
    }

    public Recipient(Long rid, Long messageId, Long recipient) {
        this.rid = rid;
        this.messageId = messageId;
        this.recipient = recipient;
    }

    public Recipient(Long messageId, Long recipient) {
        this.messageId = messageId;
        this.recipient = recipient;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "rid=" + rid +
                ", messageId=" + messageId +
                ", recipient=" + recipient +
                '}';
    }
}
