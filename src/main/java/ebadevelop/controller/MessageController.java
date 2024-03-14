package ebadevelop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ebadevelop.beans.SessionControl;
import ebadevelop.dao.MessageJson;
import ebadevelop.dao.MessageRequestJson;
import ebadevelop.entity.Message;
import ebadevelop.entity.UserList;
import ebadevelop.repository.MessageRepository;
import ebadevelop.repository.UserListRepository;

@Controller
public class MessageController {
	@Autowired
	SessionControl sessionControl;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	UserListRepository UserListRepository;
	
	@GetMapping("/users")
	public ModelAndView listUsers(ModelAndView mav) {
	    mav.setViewName("users");
	    UserList loginUser = this.sessionControl.getUser();
	    List<UserList> allUsers = this.UserListRepository.findAll();
	    mav.addObject("user", loginUser);
	    mav.addObject("peers", allUsers);
	    return mav;
	}
	@GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getIcon(@PathVariable("id") Integer id) {
		byte[] icon = this.UserListRepository.findIconById(id);
		return icon;
	}
	
	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@GetMapping("/chat/{peerId}")
	public ModelAndView showChatForm(@PathVariable("peerId") Integer peerId, ModelAndView mav) {
		mav.setViewName("chat");
		
		UserList loginUser = sessionControl.getUser();
		mav.addObject("me", loginUser);
		Optional<UserList> peerOptional = this.UserListRepository.findById(peerId);
		if (peerOptional.isEmpty()) {
			mav.setViewName("redirect:/users");
			return mav;
		}
		UserList peer = peerOptional.get();
		mav.addObject("peer", peer);
		List<Message> messages = this.messageRepository.findByUserIdsOrderByTimestamp(loginUser.getId(), peerId);
		mav.addObject("messages", messages);
		
		return mav;
	}
	
	@PostMapping("/send")
	@ResponseBody
	@Transactional
	public MessageJson send(@RequestBody MessageJson messageJson) {
		UserList sender = this.sessionControl.getUser();
		Optional<UserList> receiverOptional = this.UserListRepository.findById(messageJson.getReceiverId());
		if (receiverOptional.isEmpty()) {
			return messageJson;
		}
		
		UserList receiver = receiverOptional.get();
		String messageContent = messageJson.getMessage();
		Message sentMessage = new Message(sender, receiver, messageContent);
		this.messageRepository.saveAndFlush(sentMessage);
		logger.info(String.format("send(sender:%s, reciver:%s, message:%s)", sender, receiver, messageContent));
		return messageJson;
	}
	
	// ファイルを受け取り、保存または処理するエンドポイント
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        // ファイルが空かどうかをチェック
        if (file.isEmpty()) {	
            return "ファイルを選択してください。";
        }

        try {
            // ファイル名を取得し、ファイルの保存先を指定
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDir = Paths.get("uploads");

            // 保存先ディレクトリが存在しない場合は作成
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // ファイルを保存
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }

            return "ファイルが正常にアップロードされました。";
        } catch (IOException e) {
            return "ファイルのアップロード中にエラーが発生しました。";
        }
    }
	
	@PostMapping("/receive")
	@ResponseBody
	public List<MessageJson> receive(@RequestBody MessageRequestJson messageRequestJson) {
	    List<Message> messages;
	    LocalDateTime lastMessageDateTime = messageRequestJson.getLastMessageDateTime();

	    if (lastMessageDateTime == null) {
	        UserList loginUser = sessionControl.getUser();
	        Integer myId = loginUser.getId();
	        Integer peerId = messageRequestJson.getPeerId();
	        messages = this.messageRepository.findByUserIdsOrderByTimestamp(myId, peerId);
	    } else {
	        messages = this.messageRepository.findFromTimestamp(lastMessageDateTime);
	    }

	    List<MessageJson> messageJsons = new ArrayList<>();
	    for (Message m : messages) {
	        messageJsons.add(m.toMessageJson());
	    }
	    return messageJsons;
	}
}
