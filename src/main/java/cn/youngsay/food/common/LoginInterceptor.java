package cn.youngsay.food.common;

import cn.youngsay.food.model.UserInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session==null){
            response.sendRedirect("login.html");
            return false;
        }
        UserInfo userinfo = (UserInfo) session.getAttribute("userinfo");
        if(userinfo==null){
            response.sendRedirect("login.html");
            return false;
        }
        return true;
    }
}
