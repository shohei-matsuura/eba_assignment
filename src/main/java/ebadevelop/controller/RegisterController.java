package ebadevelop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ebadevelop.entity.UserList;
import ebadevelop.repository.UserListRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserListRepository userListRepository;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam("name") String name,
                                      @RequestParam("age") Integer age,
                                      @RequestParam("password") String password,
                                      Model model) {
        // 新しいユーザーエンティティを作成し、データベースに保存します
        UserList user = new UserList();
        user.setName(name);
        user.setAge(age);
        user.setPassword(password);
        userListRepository.save(user);

        // 登録完了メッセージをモデルに追加します
        model.addAttribute("message", "登録が完了しました");

        // 登録完了ページにリダイレクトします
        return "registration-success";
    }
}
