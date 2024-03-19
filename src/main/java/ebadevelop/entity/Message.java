package ebadevelop.entity;

import java.time.LocalDateTime;

import org.antlr.v4.runtime.misc.NotNull;

import ebadevelop.dao.MessageJson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="messages")
public class Message {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName="id")
    @NotNull
    private UserList sender;
    
    @ManyToOne
    @JoinColumn(name="receiver_id", referencedColumnName="id")
    @NotNull
    private UserList receiver;
    
    @Column(name="sent_datetime")
    @NotNull
    private LocalDateTime sentDateTime;
    
    @Column(name="message")
    @NotNull
    private String message;
    
    public Message() {
        super();
    }
    
    public Message(UserList sender, UserList receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sentDateTime = LocalDateTime.now();
    }
    
    public MessageJson toMessageJson() {
        return new MessageJson(this.sender.getId(), this.receiver.getId(), this.message, this.sentDateTime);
    }
}