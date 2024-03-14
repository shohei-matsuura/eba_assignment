package ebadevelop.dao;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageJson implements Serializable {
	private Integer senderId;
	private Integer receiverId;
	private String message;
	private LocalDateTime sentDateTime;
	
	public MessageJson() {
		super();
	}
	
	public MessageJson(Integer senderId, Integer receiverId, String message, LocalDateTime sentDateTime) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.sentDateTime = sentDateTime;
	}
}
