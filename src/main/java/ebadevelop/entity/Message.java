package ebadevelop.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;

import ebadevelop.dao.MessageJson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "sender_id", referencedColumnName = "id")
	@NotNull
	private UserList sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id", referencedColumnName = "id")
	@NotNull
	private UserList receiver;

	@Column(name = "sent_datetime")
	@NotNull
	private LocalDateTime sentDateTime;

	@Column(name = "message")
	@NotNull
	private String message;

	@Column(name = "likes")
	private Long likes; // いいねの数を表すフィールド
	
	@Transient
    private boolean likedByMe; // このメッセージにいいねを押したかどうかを示すフラグ
	
	@ManyToMany
	@JoinTable(name = "message_likes", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<UserList> likedBy = new HashSet<>();

	public Message() {
		super();
	}

	public Message(UserList sender, UserList receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.sentDateTime = LocalDateTime.now();
		this.likes = 0L; // 新しいメッセージはいいねが0件で始まると仮定します
	}

	public MessageJson toMessageJson() {
		return new MessageJson(this.sender.getId(), this.receiver.getId(), this.message, this.sentDateTime, this.likes);
	}

	// いいねを追加するメソッド
	public void addLikedBy(UserList user) {
		likedBy.add(user);
	}

	// いいね数を増やすメソッド
	public void incrementLikes() {
		if (this.likes == null) {
			this.likes = 1L;
		} else {
			this.likes++;
		}
	}
}