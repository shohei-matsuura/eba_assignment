package ebadevelop.beans;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import ebadevelop.controller.LoginForm;
import ebadevelop.entity.UserList;
import ebadevelop.repository.UserListRepository;

@Component
@SessionScope
public class SessionControl implements Serializable {
	@Autowired
	private UserListRepository userListRepository;
	private UserList user;
	private Logger logger = LoggerFactory.getLogger(SessionControl.class);
	
	public boolean login(LoginForm loginForm) {
		this.user = this.userListRepository.findByIdAndPassword(loginForm.getLoginId(), loginForm.getPassword());
		if (user == null) {
			return false;
		} else {
			this.logger.info("log in(" + this.user + ")");
			return true;
		}
	}
	
	public void logout() {
		this.user = null;
	}
	
	public boolean isLogin() {
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public UserList getUser() {
		return this.user;
	}
}
