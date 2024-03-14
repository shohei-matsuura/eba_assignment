package ebadevelop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ebadevelop.entity.UserList;
import ebadevelop.service.UserListService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/userlist")
public class UserListServlet extends HttpServlet {
	@Autowired
	UserListService UserListService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ここでデータベースからユーザーデータを取得し、JSON形式に変換してクライアントに送信する
    	List<UserList> users = UserListService.findAll();
        List<UserList> json = users;
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();	
        out.print(json);
        out.flush();
    }
}
