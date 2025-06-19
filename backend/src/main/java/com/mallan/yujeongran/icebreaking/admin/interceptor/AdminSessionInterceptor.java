package com.mallan.yujeongran.icebreaking.admin.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AdminSessionInterceptor implements HandlerInterceptor { // Check Session Key Exists

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ADMIN_LOGIN_ID") == null) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":401, \"message\":\"세션이 없습니다. 로그인 해주세요.\"}");
            return false;
        }
        return true;
    }

}
