package ebadevelop.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // 現在のセッションを取得（存在しない場合は新規作成しない）
        
        if (session != null) {
            session.invalidate(); // セッションを無効化
        }
        
        response.sendRedirect("/"); // ログアウト後のリダイレクト先を設定
    }
}

