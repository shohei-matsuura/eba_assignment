package ebadevelop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ebadevelop.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query(value="SELECT * FROM messages WHERE (sender_id = :sender_id AND receiver_id = :receiver_id) OR (sender_id = :receiver_id AND receiver_id = :sender_id) ORDER BY sent_datetime ASC", nativeQuery=true)
	public List<Message> findByUserIdsOrderByTimestamp(@Param("sender_id") Integer senderId, @Param("receiver_id") Integer receiverId);
	
	//@Query(value="SELECT * FROM messages WHERE (sender_id = :sender_id AND receiver_id = :receiver_id AND sent_datetime > :last_recv_message_datetime) OR (sender_id = :receiver_id AND receiver_id = :sender_id AND sent_datetime > :last_recv_message_datetime) ORDER BY sent_datetime ASC", nativeQuery=true)
	//public List<Message> findFromTimestamp(@Param("sender_id") Integer senderId, @Param("receiver_id") Integer receiverId, @Param("last_recv_message_datetime") LocalDateTime lastRecvMessageDatetime);
	@Query(value="SELECT * FROM messages WHERE sent_datetime > :last_recv_message_datetime", nativeQuery=true)
	public List<Message> findFromTimestamp(@Param("last_recv_message_datetime") LocalDateTime lastRecvMessageDatetime);
}
