package ebadevelop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ebadevelop.beans.SessionControl;
import ebadevelop.service.UserListService;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginContloller {
	@Autowired
	UserListService userListService;
	@Autowired
	SessionControl sessionControl;
	/**
	 * トップページへのリクエスト
	 * @return index.htmlのパス
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		// index.htmlのパスを返却する
		// 以下の場合、templates内のindex.htmlを表示する指示
		return "index";
	}
	
	  
	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm, ModelAndView mav, HttpSession session) {
	    if (this.sessionControl.login(loginForm)) {
	        // ログインが成功した場合、セッションにログインユーザーの情報を保存
	        session.setAttribute("user", this.sessionControl.getUser());
	        mav.setViewName("redirect:/home");
	    } else {
	        mav.setViewName("/");
	    }    
	    return mav;
	}
    
    @GetMapping("/home")
    public ModelAndView showLoginOKPage(ModelAndView mav) {
        mav.setViewName("login-ok");
        return mav;
    }
	}