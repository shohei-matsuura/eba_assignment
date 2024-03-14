package ebadevelop.dao;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageRequestJson implements Serializable {
	private Integer myId;
	private Integer peerId;
	private LocalDateTime lastMessageDateTime;
	
	public MessageRequestJson() {
		super();
	}
	
	public MessageRequestJson(Integer myId, Integer peerId, LocalDateTime lastMessageDateTime) {
		this.myId = myId;
		this.peerId = peerId;
		this.lastMessageDateTime = lastMessageDateTime;
	}
}
