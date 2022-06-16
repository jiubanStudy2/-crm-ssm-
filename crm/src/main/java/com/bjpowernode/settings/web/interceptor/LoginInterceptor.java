package com.bjpowernode.settings.web.interceptor;

import com.bjpowernode.commons.contants.Contants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        如果用户登录成功则返回true
        if (request.getSession().getAttribute(Contants.SESSION_USER)==null){
//            本次重定向不在控制层，所以重定向到首页需要加项目名
            response.sendRedirect(request.getContextPath());
            return false;
        }
            return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
