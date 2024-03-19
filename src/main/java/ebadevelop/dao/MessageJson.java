package ebadevelop.dao;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageJson implements Serializable {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private LocalDateTime sentDateTime;
    private Long likes;
    
    public MessageJson() {
        super();
    }
    
    public MessageJson(Integer senderId, Integer receiverId, String message, LocalDateTime sentDateTime, Long likes) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentDateTime = sentDateTime;
        this.likes = likes;
    }
    
    // idのgetterメソッドを追加
    public Integer getId() {
        return this.id;
    }
}
